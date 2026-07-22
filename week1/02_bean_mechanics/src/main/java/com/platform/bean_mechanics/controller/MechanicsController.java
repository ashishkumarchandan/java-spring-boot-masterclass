package com.platform.bean_mechanics.controller;

import com.platform.bean_mechanics.service.MechanicsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/demo")
public class MechanicsController {

    private final MechanicsService service;


    public MechanicsController(MechanicsService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Object> handleGet() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("payload", service.assemblePayload());
        return response;
    }
}
