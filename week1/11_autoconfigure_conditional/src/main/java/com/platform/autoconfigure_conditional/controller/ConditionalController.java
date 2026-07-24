package com.platform.autoconfigure_conditional.controller;

import com.platform.autoconfigure_conditional.service.PaymentProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/demo")
public class ConditionalController {

    private final PaymentProcessor processor;

    @Autowired
    public ConditionalController(PaymentProcessor processor) {
        this.processor = processor;
    }

    @GetMapping
    public Map<String, Object> handleGet(@RequestParam(value = "amount", defaultValue = "99.99") double amount) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("payload", processor.processPayment(amount));
        return response;
    }
}
