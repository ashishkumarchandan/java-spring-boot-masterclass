package com.platform.mvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/mvc")
public class ArchitectureDemoController {

    @GetMapping("/trace")
    public Map<String, Object> traceRequestLifecycle() {
        System.out.println("--- [STAGE 2: HANDLER_ADAPTER -> CONTROLLER METHOD EXECUTION] ---");
        System.out.println("⚡ Inside ArchitectureDemoController.traceRequestLifecycle()");
        
        return Map.of(
            "message", "Request routed successfully through Spring MVC DispatcherServlet Pipeline!",
            "architecture", "Embedded Tomcat -> Servlet Filter -> DispatcherServlet -> HandlerMapping -> Controller",
            "status", "SUCCESS"
        );
    }
}
