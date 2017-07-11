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
      }
      stage('Build & Push Image') {
        node ('linux-host-slave') {
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
