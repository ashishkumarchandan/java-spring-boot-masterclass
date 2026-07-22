package com.platform.bean_lifecycle.repository;

import org.springframework.stereotype.Repository;

@Repository
public class LifecycleRepository {
    public LifecycleRepository() {
        System.out.println("[LifecycleRepository] Constructor Executed.");
    }
    public String getLifecycleData() {
        return "Active database context state";
    }
}
