package com.platform.bean_scopes.component;

import com.platform.bean_scopes.service.SingletonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ScopesRunner implements CommandLineRunner {

    private final SingletonService service;

    @Autowired
    public ScopesRunner(SingletonService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================================");
        System.out.println("[ScopesRunner] Demonstrating Mixed Scope Injection:");
        System.out.println("  -> Frozen hash identity in Singleton: " + service.getFrozenProcessorHash());
        
        System.out.println("  -> Run 1 (Trap): " + service.runWithTrap("Data1"));
        System.out.println("  -> Run 2 (Trap): " + service.runWithTrap("Data2"));
        System.out.println("  -> (Observe: Same prototype instance ID/hash is printed!)");
        
        System.out.println("  -> Run 3 (Solution): " + service.runWithSolution("Data3"));
        System.out.println("  -> Run 4 (Solution): " + service.runWithSolution("Data4"));
        System.out.println("  -> (Observe: Different prototype instance IDs/hashes are printed!)");
        System.out.println("========================================================\n");
    }
}
