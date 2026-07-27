package com.platform.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PresentationLayerApplication {

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("🌐 Bootstrapping REST Presentation Layer Application...");
        System.out.println("=================================================================");
        SpringApplication.run(PresentationLayerApplication.class, args);
        System.out.println("✅ REST Endpoints exposed at http://localhost:8080/api/v1/products");
        System.out.println("=================================================================");
    }
}
