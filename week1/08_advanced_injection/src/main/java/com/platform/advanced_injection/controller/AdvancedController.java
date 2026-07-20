package com.platform.advanced_injection.controller;

import com.platform.advanced_injection.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/demo")
public class AdvancedController {

    private final ShippingService service;

    @Autowired
    public AdvancedController(ShippingService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Object> handleGet(
            @RequestParam(value = "type", defaultValue = "standard") String type,
            @RequestParam(value = "weight", defaultValue = "10.0") double weight) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("payload", service.processShipping(type, weight));
        return response;
    }
}
