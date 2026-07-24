package com.platform.framework_vs_boot.controller;

import com.platform.framework_vs_boot.service.FrameworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/demo")
public class FrameworkController {

    private final FrameworkService service;

    @Autowired
    public FrameworkController(FrameworkService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Object> handleGet() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("payload", service.executeManualWiringCheck());
        return response;
    }
}
