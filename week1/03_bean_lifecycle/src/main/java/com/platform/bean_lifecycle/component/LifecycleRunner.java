package com.platform.bean_lifecycle.component;

import com.platform.bean_lifecycle.service.LifecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LifecycleRunner implements CommandLineRunner {

    private final LifecycleService service;

    @Autowired
    public LifecycleRunner(LifecycleService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================================");
        System.out.println("[LifecycleRunner] Startup Check:");
        System.out.println("  -> Lifecycle Status: " + service.checkStatus());
        System.out.println("========================================================\n");
    }
}
