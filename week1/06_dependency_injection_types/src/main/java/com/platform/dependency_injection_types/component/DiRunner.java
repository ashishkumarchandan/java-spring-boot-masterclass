package com.platform.dependency_injection_types.component;

import com.platform.dependency_injection_types.service.DiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DiRunner implements CommandLineRunner {

    private final DiService service;

    @Autowired
    public DiRunner(DiService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================================");
        System.out.println("[DiRunner] Running Dependency Injection Comparison Demo:");
        System.out.println("  -> Service Payload: " + service.fetchResource());
        System.out.println("  -> Is Optional Logger Autowired via Setter? " + service.hasLogger());
        System.out.println("========================================================\n");
    }
}
