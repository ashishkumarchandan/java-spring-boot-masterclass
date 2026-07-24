package com.platform.autoconfigure_conditional.component;

import com.platform.autoconfigure_conditional.service.PaymentProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConditionalRunner implements CommandLineRunner {

    private final PaymentProcessor processor;

    @Autowired
    public ConditionalRunner(PaymentProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================================");
        System.out.println("[ConditionalRunner] Running Auto-Configuration Conditional Demo:");
        System.out.println("  -> Action result: " + processor.processPayment(1200.00));
        System.out.println("========================================================\n");
    }
}
