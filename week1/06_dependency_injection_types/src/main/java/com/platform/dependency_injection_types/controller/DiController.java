package com.platform.dependency_injection_types.controller;

import com.platform.dependency_injection_types.service.DiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/demo")
public class DiController {

    private final DiService service;

    @Autowired
    public DiController(DiService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Object> handleGet() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("hasOptionalLogger", service.hasLogger());
        response.put("payload", service.fetchResource());
        return response;
    }
}
