package com.tikal.rm.ci.controller;

import com.tikal.rm.ci.entity.Request;
import com.tikal.rm.ci.entity.Response;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api")
public class CiController {

    @GetMapping("/test")
    public String test(@RequestParam("param") String param) {
        return param ;
    }


    @PostMapping("/build")
    public Response build(Request request) {
        return new Response("https://github.com/tikalk/RM_CI", "SUCCESS");
    }
}
