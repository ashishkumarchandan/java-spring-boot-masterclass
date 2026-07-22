package com.platform.core_container.component;

import com.platform.core_container.repository.CoreRepository;
import com.platform.core_container.service.CoreService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CoreRunner implements CommandLineRunner {

    private final CoreService service;
    private final CoreRepository repository;

    public CoreRunner(CoreService service, CoreRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================================");
        System.out.println("[CoreRunner] Tracing Core Container Reference Mapping:");
        System.out.println("  -> CoreRepository Identity Hash: " + System.identityHashCode(repository));
        System.out.println("  -> CoreService Identity Hash: " + System.identityHashCode(service));
        System.out.println("  -> Runner calling service: " + service.processData());
        System.out.println("========================================================\n");
    }
}
