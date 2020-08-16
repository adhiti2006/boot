package com.ss.demo.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckResource {

    @RequestMapping("/heartbeat")
    @GetMapping
    boolean fetchCustomer() {
        return true;
    }
}
