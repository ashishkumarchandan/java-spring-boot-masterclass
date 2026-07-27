package com.platform.validation.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    // Regex for standard E.164 phone numbers (e.g. +14155552671)
    private static final String PHONE_REGEX = "^\\+[1-9]\\d{1,14}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // Let @NotNull or @NotBlank handle null checks if required
        }
        return value.matches(PHONE_REGEX);
    }
}
