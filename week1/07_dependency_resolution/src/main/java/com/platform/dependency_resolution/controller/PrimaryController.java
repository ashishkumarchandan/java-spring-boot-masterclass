package com.platform.dependency_resolution.controller;

import com.platform.dependency_resolution.repository.SmsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/demo/primary")
public class PrimaryController {

    private final SmsProvider smsProvider;

    @Autowired
    public PrimaryController(SmsProvider smsProvider) {
        // Will inject TwilioProvider since it is annotated with @Primary
        this.smsProvider = smsProvider;
    }

    @GetMapping
    public Map<String, Object> handleGet() {
        Map<String, Object> response = new HashMap<>();
        response.put("mode", "PRIMARY");
        response.put("providerClass", smsProvider.getClass().getSimpleName());
        response.put("action", smsProvider.sendSms("Verification code 9988"));
        return response;
    }
}
