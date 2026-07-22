package com.platform.bean_mechanics.component;

import com.platform.bean_mechanics.service.MechanicsService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MechanicsRunner implements CommandLineRunner {

    private final MechanicsService service;

    
    public MechanicsRunner(MechanicsService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================================");
        System.out.println("[MechanicsRunner] Starting up Bean Declaration Demo:");
        System.out.println("  -> Assemble Payload: " + service.assemblePayload());
        System.out.println("========================================================\n");
    }
}
