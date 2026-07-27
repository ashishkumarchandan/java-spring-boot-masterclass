# Topic 05: Defensive Input Validation (Jakarta Bean Validation)

## The First Principle: Defensive Programming & Boundary Protection

Accepting raw HTTP payload bytes into domain logic without strict validation leads to:
1. **Corrupted Database State**: Saving invalid emails, negative account balances, or null usernames.
2. **Security Vulnerabilities**: Injection attacks (SQLi, XSS) and buffer overflow vulnerabilities.
3. **Wasted CPU Cycles**: Executing complex database queries or external API calls only to crash later on missing fields.

**Jakarta Bean Validation (formerly Bean Validation / JSR 380)** defines a standard specification for validating Java objects using declarative metadata annotations. Spring Boot uses **Hibernate Validator** as the reference implementation via `spring-boot-starter-validation`.

---

## Why-Not-Just-What: Manual `if` Statements vs Declarative `@Valid`

### Manual Validation (The Flawed Way):
```java
@PostMapping("/register")
public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest req) {
    if (req.getUsername() == null || req.getUsername().isBlank()) {
        return ResponseEntity.badRequest().body("Username is required");
    }
    if (req.getEmail() == null || !req.getEmail().contains("@")) {
        return ResponseEntity.badRequest().body("Invalid email");
    }
    // Manual validation logic clutters controllers and is hard to maintain across 50 endpoints!
}
```

### Declarative Validation in Spring MVC:
By annotating controller arguments with `@Valid` (or `@Validated`), Spring MVC's `RequestMappingHandlerAdapter` automatically triggers Hibernate Validator **before** executing the controller method body. If validation fails, Spring prevents execution and throws a `MethodArgumentNotValidException`.

---

## Key Jakarta Validation Annotations Reference

| Annotation | Description | Example |
| :--- | :--- | :--- |
| `@NotNull` | Element must not be `null`. | `@NotNull Double price` |
| `@NotEmpty` | Element must not be `null` or empty (size > 0 for collections/strings). | `@NotEmpty List<String> items` |
| `@NotBlank` | String must not be `null` and trimmed length must be > 0. | `@NotBlank String username` |
| `@Size` | String, Collection, or Array size must fall within min/max boundaries. | `@Size(min = 3, max = 30)` |
| `@Email` | String must conform to valid RFC email address structure. | `@Email String email` |
| `@Min` / `@Max` | Number must be greater/less than or equal to specified bound. | `@Min(18) int age` |
| `@Pattern` | String must match specified Regular Expression regex. | `@Pattern(regexp = "^[A-Z0-9]+$")` |

---

## Custom Constraint Validator Mechanics

To build custom domain validation logic (e.g. validating phone number formats), create:
1. **Custom Annotation**: Defined with `@Constraint(validatedBy = PhoneNumberValidator.class)` and `@Retention(RetentionPolicy.RUNTIME)`.
2. **Constraint Validator Class**: Implements `ConstraintValidator<ValidPhoneNumber, String>` implementing the custom evaluation method `isValid()`.

```java
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    private static final String PHONE_REGEX = "^\\+[1-9]\\d{1,14}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.matches(PHONE_REGEX);
    }
}
```
