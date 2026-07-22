package com.platform.bean_mechanics.repository;

import org.springframework.stereotype.Repository;

@Repository
public class MechanicsRepository {
    public String loadLocalData() {
        return "Local Database Config Record";
    }
}
