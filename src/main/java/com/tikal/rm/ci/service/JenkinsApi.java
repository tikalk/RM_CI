package com.tikal.rm.ci.service;


import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.QueueReference;
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

@Service
public class JenkinsApi {

    @Autowired
    Producer producer;


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
        System.out.println();
    }


    public void runBuild(JobType jobType, String gitUrl, String buildCommand) throws IOException {


        String jankinsJobName = env.getProperty(jobType.getJobName());

        JobWithDetails test = jenkinsServer.getJob(jankinsJobName);


        Map<String, String> params = new HashMap<>();

        params.put("repo_url", gitUrl);
        params.put("build_command", buildCommand);


        QueueReference build = test.build(params);

//        producer.produce(gitUrl, "test-jenkins-url", "SUCCESS");


    }

}
