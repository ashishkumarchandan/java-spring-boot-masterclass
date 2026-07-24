package com.platform.springboot_bootstrap.repository;

import org.springframework.stereotype.Repository;

@Repository
public class BootstrapRepository {
    public String checkStatus() {
        return "Operational";
    }
}
