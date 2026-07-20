package com.platform.coupling_and_ioc.component;

import com.platform.coupling_and_ioc.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CouplingRunner implements CommandLineRunner {

    private final PaymentService service;

    @Autowired
    public CouplingRunner(PaymentService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================================");
        System.out.println("[CouplingRunner] Running Decoupled Architecture Demo:");
        System.out.println("  -> Action: " + service.processPayment("$500.00"));
        System.out.println("========================================================\n");
    }
}
