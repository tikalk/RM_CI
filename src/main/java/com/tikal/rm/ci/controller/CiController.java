package com.tikal.rm.ci.controller;

import com.tikal.rm.ci.entity.Request;
import com.tikal.rm.ci.entity.Response;
import com.tikal.rm.ci.service.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api")
public class CiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // temporary
    @Autowired
    Producer producer ;


    @GetMapping("/test")
    public String test(@RequestParam("param") String param) {
        logger.debug("=============================================");
        logger.debug("===== test (param=" + param + ")  ===================");
        return param ;
    }


    @PostMapping("/build")
    public Response build(Request request) {

        //  call service JenkinsApi.build()

        // temporary code, calling sender to send to rabbit mq (this should be called by service when it completes)
        producer.produce(request.getGitUrl(), "test-jenkins-url", "SUCCESS", "");


        return new Response("https://github.com/tikalk/RM_CI", "SUCCESS");
    }
}
