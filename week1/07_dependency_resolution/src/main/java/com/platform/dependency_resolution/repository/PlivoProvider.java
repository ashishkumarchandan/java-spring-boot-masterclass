package com.platform.dependency_resolution.repository;

import org.springframework.stereotype.Repository;

@Repository("plivoProvider")
public class PlivoProvider implements SmsProvider {
    @Override
    public String sendSms(String msg) {
        return "Plivo API -> Dispatching: " + msg;
    }
}
