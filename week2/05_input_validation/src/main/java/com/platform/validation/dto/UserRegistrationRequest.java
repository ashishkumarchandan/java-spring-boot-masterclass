package com.platform.validation.dto;

import com.platform.validation.annotation.ValidPhoneNumber;
import jakarta.validation.constraints.*;

public class UserRegistrationRequest {

    @NotBlank(message = "Username cannot be empty or blank")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 64, message = "Password must be at least 8 characters long")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit")
    private String password;

    @Min(value = 18, message = "User must be at least 18 years old")
    @Max(value = 120, message = "Age cannot exceed 120")
    private int age;

    @ValidPhoneNumber
    private String phoneNumber;

    public UserRegistrationRequest() {
    }

    public UserRegistrationRequest(String username, String email, String password, int age, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
