package com.platform.autoconfigure_conditional.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "payment.mock.enabled", havingValue = "true")
public class MockPaymentGateway implements PaymentGateway {
    @Override
    public String transfer(double amount) {
        return "SUCCESS (Simulated) -> Cost: $0.00";
    }

    @Override
    public String getMode() {
        return "Staging Sandbox Mode";
    }
}
