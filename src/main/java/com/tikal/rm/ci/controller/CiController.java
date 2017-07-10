package com.tikal.rm.ci.controller;

import com.tikal.rm.ci.entity.Request;
import com.tikal.rm.ci.entity.Response;
import com.tikal.rm.ci.service.AsyncService;
import com.tikal.rm.ci.service.JenkinsApi;
import com.tikal.rm.ci.service.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController()
@RequestMapping("/api")
public class CiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // temporary
    //@Autowired
    //Producer producer ;

    @Autowired
    AsyncService asyncService;

    @GetMapping("/test")
    public String test(@RequestParam("param") String param) {
        logger.debug("=============================================");
        logger.debug("===== test (param=" + param + ")  ===================");
        return param ;
    }


    @PostMapping("/build")
    public String build(@RequestBody Request request) {

        logger.debug("=============================================");
        logger.debug("===== build (request=" + request + ")  ===================");
        // cal service asynchronously
        asyncService.callService(request);


        return "{\"status\": \"OK\"}";
    }



    // cal service asynchronously
//    @Async
//    public void callService(Request request) {
//        logger.info("calling service on a separate thread");
//        //  call service JenkinsApi.build()
//        try {
//            jenkinsApi.runBuild(request.getGitUrl(), request.getCommand());
//        } catch (IOException e) {
//            logger.error("absorb", e);
//        }
//
//        // temporary code, calling sender to send to rabbit mq (this should be called by service when it completes)
//        //producer.produce(request.getGitUrl(), "test-jenkins-url", "SUCCESS");
//
//    }
}
