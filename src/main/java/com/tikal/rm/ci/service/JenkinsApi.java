package com.tikal.rm.ci.service;


import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@Service
public class JenkinsApi {

//    @Autowired
//    Producer producer;

    static private Logger log = Logger.getLogger(JenkinsApi.class.getSimpleName());
    private JenkinsServer jenkinsServer;
    private JenkinsHttpClient jenkinsHttpClient;

    @Autowired
    private Environment env;
    @Value("${server.url}")
    private String jenkinsServerUrl;
    @Value("${jenkins.user}")
    private String user;
    @Value("${jenkins.password}")
    private String password;

    @PostConstruct
    private void init() throws URISyntaxException {
        jenkinsHttpClient = new JenkinsHttpClient(new URI(jenkinsServerUrl), user, password);
        jenkinsServer = new JenkinsServer(jenkinsHttpClient);

    }


    public void runBuild(JobType jobType, String gitUrl, String buildCommand) throws InterruptedException, IOException {


        String jenkinsJobName = env.getProperty(jobType.getJobName());

        JobWithDetails job = jenkinsServer.getJob(jenkinsJobName);

        Map<String, String> params = new HashMap<>();

        params.put("repo_url", gitUrl);
        params.put("build_command", buildCommand);


        QueueReference queueReference = job.build(params);


        String queueItemUrlPart = queueReference.getQueueItemUrlPart();
        log.info("queueItemUrlPart: " + queueItemUrlPart);
        QueueItem queueItem = jenkinsServer.getQueueItem(new QueueReference(queueItemUrlPart));

        do {
            Thread.sleep(2000L);
            queueItem = jenkinsServer.getQueueItem(queueReference);
        } while (queueItem.getExecutable() == null);

        Build build_ = jenkinsServer.getBuild(queueItem);


        Build buildByNumber;
        int number = build_.getNumber();
        log.info("buildByNumber: " + number);
        do {
            Thread.sleep(2000L);
            job = jenkinsServer.getJob(jenkinsJobName);
            buildByNumber = job.getBuildByNumber(number);
        } while (buildByNumber == null);


        BuildWithDetails details;

        do {
            Thread.sleep(1000L);
            details = buildByNumber.details();
        } while (details.isBuilding() );

        BuildResult result = details.getResult();

        log.info("result: " + result.name());
//        producer.produce(gitUrl, "test-jenkins-url", result.name());


    }
}
