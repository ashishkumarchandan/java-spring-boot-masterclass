package com.platform.framework_vs_boot.service;

import com.platform.framework_vs_boot.repository.FrameworkRepository;

// Simulating traditional service class without Spring annotations
public class FrameworkService {
    private final FrameworkRepository repository;

    public FrameworkService(FrameworkRepository repository) {
        this.repository = repository;
    }

    public String executeManualWiringCheck() {
        return "FrameworkService -> " + repository.loadConfig();
    }
}
