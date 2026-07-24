package com.platform.autoconfigure_conditional.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "payment.mock.enabled", havingValue = "false", matchIfMissing = true)
public class ProductionPaymentGateway implements PaymentGateway {
    @Override
    public String transfer(double amount) {
        return "SUCCESS (Live Ledger) -> Transferred: $" + amount + " via Visa/Mastercard Core Network";
    }

    @Override
    public String getMode() {
        return "Live Production Mode";
    }
}
