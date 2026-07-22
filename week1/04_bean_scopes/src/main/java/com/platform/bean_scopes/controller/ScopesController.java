package com.platform.bean_scopes.controller;

import com.platform.bean_scopes.service.SingletonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/demo")
public class ScopesController {

    private final SingletonService service;

    @Autowired
    public ScopesController(SingletonService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Object> handleGet() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        
        // Execute calls to demonstrate the trap vs solution
        response.put("frozenCall1", service.runWithTrap("PayloadA"));
        response.put("frozenCall2", service.runWithTrap("PayloadB"));
        
        response.put("freshCall1", service.runWithSolution("PayloadX"));
        response.put("freshCall2", service.runWithSolution("PayloadY"));
        
        return response;
    }
}
