package com.platform.dependency_injection_types.repository;

import org.springframework.stereotype.Repository;

@Repository
public class DiRepository {
    public String fetchSecurePayload() {
        return "Decoded enterprise payload from secure tables.";
    }
}
