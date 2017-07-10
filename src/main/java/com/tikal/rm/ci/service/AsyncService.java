package com.tikal.rm.ci.service;

import com.tikal.rm.ci.entity.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AsyncService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JenkinsApi jenkinsApi ;

    // temporary
    @Autowired
    Producer producer ;


    @Async
    public void callService(Request request) {
        logger.info("calling service on a separate thread");
        //  call service JenkinsApi.build()
        try {
            jenkinsApi.runBuild(JobType.JAVA, request.getGitUrl(), request.getCommand());
        } catch (Exception e) {
            logger.error("absorb", e);
        }

        // temporary code, calling sender to send to rabbit mq (this should be called by service when it completes)
        //producer.produce(request.getGitUrl(), "test-jenkins-url", "SUCCESS");

    }
}
