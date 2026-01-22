package com.demo.devfun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PingController {

    @GetMapping(value = "/ping", produces = "application/json")
    public Object healthCheck() {
        Map<String, Object> map = new HashMap<>();
        map.put("today", ZonedDateTime.now().getMonth() + ":" + ZonedDateTime.now().getDayOfMonth());
        return map;
    }
}
