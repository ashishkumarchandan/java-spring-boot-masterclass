package com.platform.dependency_resolution.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository("twilioProvider")
@Primary // Default fallback when type is requested without qualifiers
public class TwilioProvider implements SmsProvider {
    @Override
    public String sendSms(String msg) {
        return "Twilio API -> Dispatching: " + msg;
    }
}
