package com.platform.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InputValidationApplication {

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("🛡️ Bootstrapping Input Validation & Jakarta Constraints App...");
        System.out.println("=================================================================");
        SpringApplication.run(InputValidationApplication.class, args);
        System.out.println("✅ User validation API ready at http://localhost:8080/api/v1/users");
        System.out.println("=================================================================");
    }
}
