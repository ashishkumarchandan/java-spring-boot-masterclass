# Topic 06: Centralized Exception Handling (`@RestControllerAdvice`)

## The First Principle: Uncaught Exceptions & Security Hazards

When an unhandled `Exception` is thrown inside a Controller, Service, or Repository, execution abruptly exits the normal call stack. Without a centralized exception handler:
1. **Raw Stack Trace Leaks**: Tomcat renders default HTML 500 error pages containing full Java stack traces, revealing internal package names, database schema details, and third-party library versions to malicious actors.
2. **Inconsistent Error Contracts**: Different controllers return wildly different error JSON structures (or raw strings), breaking frontend client deserialization.
3. **No HTTP Status Mapping**: Business failures (e.g. "User Not Found") result in generic `500 Internal Server Error` instead of semantic HTTP codes (`404 Not Found`, `422 Unprocessable Entity`).

---

## Why-Not-Just-What: Try-Catch Clutter vs `@RestControllerAdvice`

### Traditional Try-Catch (The Flawed Way):
```java
@GetMapping("/{id}")
public ResponseEntity<?> getAccount(@PathVariable String id) {
    try {
        return ResponseEntity.ok(accountService.find(id));
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error");
    }
}
```
**Why this breaks down:**
- Duplicates dozens of `try-catch` blocks across every controller action.
- Obscures happy-path business logic readability.

### Global Exception Interception with `@RestControllerAdvice`:
Spring MVC uses `HandlerExceptionResolver` components inside `DispatcherServlet`. `@RestControllerAdvice` is a specialized `@Component` that automatically intercepts any exception thrown anywhere during HTTP request handling across **all `@RestController` components**.

```text
==========================================================================================
                     GLOBAL EXCEPTION HANDLING ARCHITECTURE
==========================================================================================

   [ Controller / Service / Repository ] 
                  |
                  v (Throws ResourceNotFoundException)
   [ DISPATCHER SERVLET ] (Catches Unhandled Exception)
                  |
                  v
   [ HandlerExceptionResolver Composite ]
                  |
                  v
   [ @RestControllerAdvice (GlobalExceptionHandler) ]
                  |
                  +---> Matched @ExceptionHandler(ResourceNotFoundException.class)
                  |
                  v
   [ Serializes ErrorResponse Record to RFC 7807 JSON Payload ]
                  |
                  v
   [ Returns HTTP 404 NOT FOUND with Predictable Error Schema ]
==========================================================================================
```

---

## RFC 7807 Standard Error Response Structure

Our global exception handler standardizes all API errors using the standard `ErrorResponse` payload:

```json
{
  "timestamp": "2026-07-26T10:45:12.891",
  "status": 404,
  "error": "Not Found",
  "message": "Account with ID 999 does not exist.",
  "path": "/api/v1/accounts/999",
  "details": []
}
```

---

## cURL Error Testing Commands

```bash
# 1. Test 404 Resource Not Found Exception
curl -i -X GET http://localhost:8080/api/v1/accounts/999

# 2. Test 422 Business Rule Violation Exception
curl -i -X POST "http://localhost:8080/api/v1/accounts/101/withdraw?amount=15000"

# 3. Test 500 Unhandled System Bug Fallback
curl -i -X GET http://localhost:8080/api/v1/accounts/bug
```
