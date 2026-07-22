package com.platform.bean_scopes.repository;

import org.springframework.stereotype.Repository;

@Repository
public class ScopesRepository {
    public String getScopeMeta() {
        return "Scopes Repository active.";
    }
}
