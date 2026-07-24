package com.platform.springboot_bootstrap.service;

import com.platform.springboot_bootstrap.repository.BootstrapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BootstrapService {
    private final BootstrapRepository repository;

    @Autowired
    public BootstrapService(BootstrapRepository repository) {
        this.repository = repository;
    }

    public String testService() {
        return "BootstrapService -> Database status: " + repository.checkStatus();
    }
}
