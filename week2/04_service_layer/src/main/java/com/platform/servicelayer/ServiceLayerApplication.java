package com.platform.servicelayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceLayerApplication {

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("💼 Bootstrapping Service Layer & Transaction Management App...");
        System.out.println("=================================================================");
        SpringApplication.run(ServiceLayerApplication.class, args);
        System.out.println("✅ Order API ready at http://localhost:8080/api/v1/orders");
        System.out.println("=================================================================");
    }
}
