package com.demo.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RefreshScope enables dynamic config refresh from Nacos without restart.
 * Change "greeting.message" in Nacos console and see it update live.
 */
@RefreshScope
@RestController
public class GreetingController {

    @Value("${greeting.message:Hello from Provider}")
    private String greetingMessage;

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return greetingMessage + ", " + name + "!";
    }

    @GetMapping("/hello")
    public String hello() {
        return greetingMessage;
    }
}
