package com.platform.validation.controller;

import com.platform.validation.dto.UserRegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserValidationController {

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        System.out.println("✅ Payload passed validation for user: " + request.getUsername());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", "SUCCESS",
                "message", "User registration payload validated successfully!",
                "username", request.getUsername(),
                "email", request.getEmail()
        ));
    }
}
