package com.tikal.rm.ci.service;


import com.offbytwo.jenkins.JenkinsServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class JenkinsApi {

    @Autowired
    Producer producer ;
    private JenkinsServer jenkinsServer;
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
        jenkinsServer = new JenkinsServer(new URI(jenkinsServerUrl), user, password);
        System.out.println();
    }


    public void runBuild(JobType jobType, String gitUrl, String jobCommand) throws IOException {


        String jankinsJobName = env.getProperty(jobType.getJobName());


        producer.produce(gitUrl, "test-jenkins-url", "SUCCESS");




//        jenkinsServer.getJobs();



    }

}
