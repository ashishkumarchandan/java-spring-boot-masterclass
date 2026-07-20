package com.platform.dependency_resolution.controller;

import com.platform.dependency_resolution.repository.SmsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/demo/qualified")
public class QualifiedController {

    private final SmsProvider smsProvider;

    @Autowired
    public QualifiedController(@Qualifier("plivoProvider") SmsProvider smsProvider) {
        // Will inject PlivoProvider explicitly via @Qualifier, overriding @Primary TwilioProvider
        this.smsProvider = smsProvider;
    }

    @GetMapping
    public Map<String, Object> handleGet() {
        Map<String, Object> response = new HashMap<>();
        response.put("mode", "QUALIFIED");
        response.put("providerClass", smsProvider.getClass().getSimpleName());
        response.put("action", smsProvider.sendSms("Verification code 9988"));
        return response;
    }
}
