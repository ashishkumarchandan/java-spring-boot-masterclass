package com.platform.core_container.repository;

import org.springframework.stereotype.Repository;

@Repository
public class CoreRepository {
    public CoreRepository() {
        System.out.println("[CoreRepository] Instantiated by Spring Container. Hash: " + System.identityHashCode(this));
    }

    public String getData() {
        return "Simulated Core Database Record #401";
    }
}
