package com.kangcong.yian.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 *
 * @author kangcong
 * @date 2024-06-27
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestParam String yourName) {
        return "hello " + yourName + "!";
    }
} 