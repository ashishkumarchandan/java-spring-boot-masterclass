package com.platform.responsetransformation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResponseTransformationApplication {

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("✨ Bootstrapping Standardized Response Transformation App...");
        System.out.println("=================================================================");
        SpringApplication.run(ResponseTransformationApplication.class, args);
        System.out.println("✅ Response Transformation API ready at http://localhost:8080/api/v1/demo");
        System.out.println("=================================================================");
    }
}
