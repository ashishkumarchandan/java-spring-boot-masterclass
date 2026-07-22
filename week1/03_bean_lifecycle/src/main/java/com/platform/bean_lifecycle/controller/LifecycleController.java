package com.platform.bean_lifecycle.controller;

import com.platform.bean_lifecycle.service.LifecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/demo")
public class LifecycleController {

    private final LifecycleService service;

    @Autowired
    public LifecycleController(LifecycleService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Object> handleGet() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("payload", service.checkStatus());
        return response;
    }
}
