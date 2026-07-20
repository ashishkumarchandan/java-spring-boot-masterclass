package com.platform.advanced_injection.repository;

import org.springframework.stereotype.Repository;

@Repository("standard")
public class StandardShipping implements ShippingStrategy {
    @Override
    public double calculateCost(double weight) {
        return weight * 1.5;
    }

    @Override
    public String getModeName() {
        return "Standard Ground Shipping";
    }
}
