package com.platform.responsetransformation.controller;

import com.platform.responsetransformation.dto.ApiResponse;
import com.platform.responsetransformation.dto.UserDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoResponseController {

    // 1. Controller returning raw DTO -> Intercepted & wrapped automatically by GlobalResponseAdvice
    @GetMapping("/user")
    public UserDto getUser() {
        return new UserDto(101L, "Alice Smith", "alice@platform.com", "ADMIN");
    }

    // 2. Controller returning raw List -> Wrapped automatically
    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return List.of(
                new UserDto(101L, "Alice Smith", "alice@platform.com", "ADMIN"),
                new UserDto(102L, "Bob Jones", "bob@platform.com", "DEVELOPER")
        );
    }

    // 3. Controller returning raw String -> Wrapped automatically
    @GetMapping("/ping")
    public String ping() {
        return "PONG";
    }

    // 4. Controller returning explicit ApiResponse -> Kept as-is
    @GetMapping("/custom")
    public ApiResponse<String> customEnvelope() {
        return ApiResponse.success("Custom payload", "Operation executed with custom message");
    }
}
