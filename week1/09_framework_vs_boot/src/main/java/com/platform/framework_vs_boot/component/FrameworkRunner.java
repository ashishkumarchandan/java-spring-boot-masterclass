package com.platform.framework_vs_boot.component;

import com.platform.framework_vs_boot.service.FrameworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FrameworkRunner implements CommandLineRunner {

    private final FrameworkService service;

    @Autowired
    public FrameworkRunner(FrameworkService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================================");
        System.out.println("[FrameworkRunner] Running Manual Configuration Demo:");
        System.out.println("  -> Action: " + service.executeManualWiringCheck());
        System.out.println("========================================================\n");
    }
}
