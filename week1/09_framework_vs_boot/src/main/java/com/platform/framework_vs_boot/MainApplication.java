package com.platform.framework_vs_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// We disable automatic scanning of the service and repository packages to simulate
// traditional Spring configuration, forcing manual wiring in ManualConfig.
@SpringBootApplication(scanBasePackages = {"com.platform.framework_vs_boot.controller", "com.platform.framework_vs_boot.component"})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
