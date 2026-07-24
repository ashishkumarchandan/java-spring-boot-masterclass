package com.platform.springboot_bootstrap.component;

import com.platform.springboot_bootstrap.service.BootstrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapRunner implements CommandLineRunner {

    private final BootstrapService service;

    @Autowired
    public BootstrapRunner(BootstrapService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================================");
        System.out.println("[BootstrapRunner] Startup script runner executed!");
        System.out.println("  -> " + service.testService());
        System.out.println("========================================================\n");
    }
}
