package com.arango.auction.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @GetMapping(value = "/health")
    public String healthCheck(){
        System.out.println("Running");
        return "Running";
    }
}
