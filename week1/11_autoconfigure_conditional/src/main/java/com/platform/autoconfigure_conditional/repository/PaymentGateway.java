package com.platform.autoconfigure_conditional.repository;

public interface PaymentGateway {
    String transfer(double amount);
    String getMode();
}
