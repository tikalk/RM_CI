package com.tikal.rm.ci.service;


import com.offbytwo.jenkins.JenkinsServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class JenkinsApi {

    JenkinsServer jenkinsServer;

    @Value("server.url")
    private String jenkinsServerUrl;
    @Value("user")
    private String user;
    @Value("password")
    private String password;

    @PostConstruct
    private void init() throws URISyntaxException {
        jenkinsServer = new JenkinsServer(new URI(jenkinsServerUrl), user, password);
    }


    public void runBuild(String gitUrl, String jobCommand) throws IOException {


//        jenkinsServer.createJob("","");
//
//        JobWithDetails job = jenkinsServer.getJob("");
//
//        job.

    }

}
