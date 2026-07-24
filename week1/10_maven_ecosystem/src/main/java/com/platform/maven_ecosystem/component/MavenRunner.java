package com.platform.maven_ecosystem.component;

import com.platform.maven_ecosystem.service.MavenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MavenRunner implements CommandLineRunner {

    private final MavenService service;

    @Autowired
    public MavenRunner(MavenService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================================");
        System.out.println("[MavenRunner] Running Multi-Module Parent coordination audit:");
        System.out.println("  -> " + service.runBuildAudit());
        System.out.println("========================================================\n");
    }
}
