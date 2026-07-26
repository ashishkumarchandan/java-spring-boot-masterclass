package com.platform.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MvcArchitectureApplication {

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("🚀 Bootstrapping Spring Boot Embedded Tomcat & MVC Pipeline...");
        System.out.println("=================================================================");
        SpringApplication.run(MvcArchitectureApplication.class, args);
        System.out.println("✅ DispatcherServlet registered & mapped to '/' root pattern.");
        System.out.println("🌐 Server running on http://localhost:8080");
        System.out.println("=================================================================");
    }
}
