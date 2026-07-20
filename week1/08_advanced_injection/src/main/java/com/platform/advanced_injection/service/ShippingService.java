package com.platform.advanced_injection.service;

import com.platform.advanced_injection.repository.ShippingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShippingService {

    // Spring autowires all beans implementing ShippingStrategy into this Map
    // mapping bean identifiers (e.g. "standard", "express") to their bean instances
    private final Map<String, ShippingStrategy> strategies;

    @Autowired
    public ShippingService(Map<String, ShippingStrategy> strategies) {
        this.strategies = strategies;
        System.out.println("[ShippingService] Instantiated. Strategies injected: " + strategies.keySet());
    }

    public String processShipping(String type, double weight) {
        ShippingStrategy strategy = strategies.get(type.toLowerCase());
        if (strategy == null) {
            return "Error: Unknown shipping type '" + type + "'. Available: " + strategies.keySet();
        }
        double cost = strategy.calculateCost(weight);
        return "Route: " + strategy.getModeName() + " | Weight: " + weight + " kg | Cost: $" + cost;
    }

    public Map<String, ShippingStrategy> getStrategies() {
        return strategies;
    }
}
