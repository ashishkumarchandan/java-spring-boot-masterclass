package com.platform.bean_lifecycle.service;

import com.platform.bean_lifecycle.repository.LifecycleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LifecycleService {

    private final LifecycleRepository repository;
    private boolean isReady = false;

    @Autowired
    public LifecycleService(LifecycleRepository repository) {
        this.repository = repository;
        System.out.println("[LifecycleService] STEP 1: Constructor executed. Dependencies mapped. Ready status: " + isReady);
    }

    @PostConstruct
    public void init() {
        isReady = true;
        System.out.println("[LifecycleService] STEP 2: @PostConstruct hook executed. Service is fully initialized and operational.");
    }

    @PreDestroy
    public void cleanup() {
        isReady = false;
        System.out.println("[LifecycleService] STEP 3: @PreDestroy hook executed. Releasing resources and database handlers.");
    }

    public String checkStatus() {
        return "Service Ready? " + isReady + " | Data: " + repository.getLifecycleData();
    }
}
