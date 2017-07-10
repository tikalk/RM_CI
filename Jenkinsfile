import groovy.json.*

def waitForServices() {
  sh "./kubectl get svc --sort-by=.metadata.name --namespace=fuze -o json > services.json --kubeconfig=\$(pwd)/kconfig"
  while(!toServiceMap(readFile('services.json')).containsKey('rm_ci')) {
        sleep(10)
        echo "Services are not yet ready, waiting 10 seconds"
        sh "./kubectl get svc --sort-by=.metadata.name --namespace=fuze -o json > services.json --kubeconfig=\$(pwd)/kconfig"
  }
  echo "Services are ready, continuing"
}

@com.cloudbees.groovy.cps.NonCPS
Map toServiceMap(servicesJson) {
  def json = new JsonSlurper().parseText(servicesJson)
  def serviceMap = [:]
  json.items.each { i ->
    def serviceName = i.metadata.name
    def ingress = i.status.loadBalancer.ingress
    if(ingress != null) {
      def serviceUrl = ingress[0].hostname
      serviceMap.put(serviceName, serviceUrl)
    }
  }
  return serviceMap
}

def sh_out(cmd){
   sh(script: cmd, returnStdout:true).trim()
}

node ('master') {
  def registry = "https://329054710135.dkr.ecr.eu-west-2.amazonaws.com"
  withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'k8s-aws-ecr', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']]) {
    withEnv(['AWS_ECR_LOGIN=true', 'AWS_ECR_LOGIN_REGISTRY_IDS=329054710135', 'AWS_DEFAULT_REGION=eu-west-2', 'AWS_REGION=eu-west-2']) {
      stage ('Prepare') {
        deleteDir()
        checkout scm
        gitcommit_email = sh_out('git --no-pager show -s --format=\'%ae\'')
        currentBuild.displayName = "#${BUILD_NUMBER} ${gitcommit_email}"
        sh_out("""
        curl -LO https://storage.googleapis.com/kubernetes-release/release/\$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl
        chmod +x ./kubectl
        \${HOME}/.local/bin/aws s3 cp s3://k8s-hub-tikal-io/hub.tikal.io/kconfig .
        ./kubectl apply -f svc.yaml --kubeconfig=\$(pwd)/kconfig --namespace fuze
        """)
        waitForServices()
      }
      stage('Build & Push Image') {
        node ('linux-host-slave') {
        def mvnHome = tool 'M3'
            sh "${mvnHome}/bin/mvn clean package"
          withEnv(['AWS_ECR_LOGIN=true', 'AWS_ECR_LOGIN_REGISTRY_IDS=329054710135', 'AWS_DEFAULT_REGION=eu-west-2', 'AWS_REGION=eu-west-2']) {
            sh(script: "\$(\${HOME}/.local/bin/aws ecr get-login --no-include-email &> /dev/null)", returnStdout:false)
            sh "cp \${HOME}/.docker/config.json \${HOME}/.dockercfg"
            withDockerRegistry([credentialsId: 'ecr:eu-west-2:k8s-aws-ecr', url: "${registry}"]) {
              git 'https://github.com/tikalk/rm_ci.git'
              def image = docker.build("329054710135.dkr.ecr.eu-west-2.amazonaws.com/rm_ci:${BUILD_NUMBER}")
              image.push()
            }
          }
        }
      }
      stage ('Deploy to K8S') {
        sh(script: """
        sed -i 's/BUILDNUMBER/${BUILD_NUMBER}/g' deployment.yaml
        ./kubectl apply -f deployment.yaml --kubeconfig=\$(pwd)/kconfig --namespace fuze
        ./kubectl get pods --namespace fuze -l app=rm_ci &> /dev/null
        """, returnStatus: false, returnStdout: false)
      }
    }
  }
}
