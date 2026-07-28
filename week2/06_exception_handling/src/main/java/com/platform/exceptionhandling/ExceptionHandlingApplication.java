package com.platform.exceptionhandling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExceptionHandlingApplication {

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("⚠️ Bootstrapping Global Exception Handling & Advice App...");
        System.out.println("=================================================================");
        SpringApplication.run(ExceptionHandlingApplication.class, args);
        System.out.println("✅ Account Demo API ready at http://localhost:8080/api/v1/accounts");
        System.out.println("=================================================================");
    }
}
