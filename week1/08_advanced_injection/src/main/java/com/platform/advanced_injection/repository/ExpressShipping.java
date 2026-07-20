package com.platform.advanced_injection.repository;

import org.springframework.stereotype.Repository;

@Repository("express")
public class ExpressShipping implements ShippingStrategy {
    @Override
    public double calculateCost(double weight) {
        return weight * 5.0 + 10.0;
    }

    @Override
    public String getModeName() {
        return "Express Air Delivery";
    }
}
