package com.tikal.rm.ci.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class CiController {

    @GetMapping("/test")
    public String test(@RequestParam("param") String param) {
        return param ;
    }
}
