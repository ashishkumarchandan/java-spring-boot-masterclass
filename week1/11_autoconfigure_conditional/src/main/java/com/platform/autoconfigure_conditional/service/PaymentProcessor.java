package com.platform.autoconfigure_conditional.service;

import com.platform.autoconfigure_conditional.repository.PaymentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentProcessor {
    private final PaymentGateway gateway;

    @Autowired
    public PaymentProcessor(PaymentGateway gateway) {
        // Because of ConditionalOnProperty, only ONE implementation is registered.
        // There is zero type ambiguity at autowire time!
        this.gateway = gateway;
        System.out.println("[PaymentProcessor] Injected active gateway: " + gateway.getMode());
    }

    public String processPayment(double amount) {
        return "Gateway Mode: " + gateway.getMode() + " | Result: " + gateway.transfer(amount);
    }
}
