package com.platform.advanced_injection.repository;

public interface ShippingStrategy {
    double calculateCost(double weight);
    String getModeName();
}
