package com.platform.advanced_injection.component;

import com.platform.advanced_injection.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdvancedRunner implements CommandLineRunner {

    private final ShippingService service;

    @Autowired
    public AdvancedRunner(ShippingService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================================");
        System.out.println("[AdvancedRunner] Running Collection Autowiring Strategy Demo:");
        System.out.println("  -> Active strategies registered: " + service.getStrategies().keySet());
        System.out.println("  -> Query standard: " + service.processShipping("standard", 15.0));
        System.out.println("  -> Query express: " + service.processShipping("express", 15.0));
        System.out.println("========================================================\n");
    }
}
