package com.platform.core_container.controller;

import com.platform.core_container.service.CoreService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/demo")
public class CoreController {

    private final CoreService service;

    public CoreController(CoreService service) {
        this.service = service;
        System.out.println("[CoreController] Instantiated by Spring Container. Autowired CoreService Hash: "
                + System.identityHashCode(service));
    }

    @GetMapping
    public Map<String, Object> handleGet() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("controllerHash", System.identityHashCode(this));
        response.put("serviceHash", System.identityHashCode(service));
        response.put("payload", service.processData());
        return response;
    }
}
