package com.platform.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {

    String message() default "Invalid phone number format. Expected format: +[country_code][10-digit number] (e.g. +14155552671)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
