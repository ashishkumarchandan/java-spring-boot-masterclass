package com.platform.responsetransformation.dto;

public record UserDto(
    Long id,
    String fullName,
    String email,
    String role
) {}
