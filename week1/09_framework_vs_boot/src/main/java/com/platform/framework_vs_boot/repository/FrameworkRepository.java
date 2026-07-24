package com.platform.framework_vs_boot.repository;

// Simulating traditional repository class without Spring annotations
public class FrameworkRepository {
    private final String dbUrl;

    public FrameworkRepository(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String loadConfig() {
        return "Manual Repository Connection established to: " + dbUrl;
    }
}
