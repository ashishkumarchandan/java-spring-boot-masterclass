package com.platform.coupling_and_ioc.controller;

import com.platform.coupling_and_ioc.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/demo")
public class CouplingController {

    private final PaymentService service;

    @Autowired
    public CouplingController(PaymentService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Object> handleGet(@RequestParam(value = "amount", defaultValue = "$25.00") String amount) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("payload", service.processPayment(amount));
        return response;
    }
}
