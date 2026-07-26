# Week 2: Building the Web Application (Spring Boot)

Welcome to **Week 2** of the Java & Spring Boot Masterclass! This week transitions from core Spring container mechanics to building enterprise-grade, robust, production-ready RESTful web applications using Spring Boot Web, Data JPA, Validation, and Centralized Error & Response Pipelines.

---

## 📂 Module Reference Guide

Week 2 is structured into **7 self-contained Maven sub-projects**. Each module provides runnable code and an in-depth `README.md` detailing hardware/JVM concepts, internal mechanics, and first-principles trade-offs.

### [01. MVC Architecture](./01_mvc_architecture) (`01_mvc_architecture`)
- **Focus**: Understanding the end-to-end HTTP request processing pipeline.
- **Key Concepts**: Embedded Tomcat, Servlet Container, `DispatcherServlet` Front Controller, `HandlerMapping`, `HandlerAdapter`, `HttpMessageConverter`, `HandlerInterceptor`.
- **Trace Output**: Step-by-step console lifecycle logs tracing request dispatching timing and execution stages.

### [02. Presentation Layer](./02_presentation_layer) (`02_presentation_layer`)
- **Focus**: Building RESTful APIs with Spring Web annotations.
- **Key Concepts**: `@RestController`, `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@PathVariable`, `@RequestParam`, `@RequestBody`, `@RequestHeader`, `ResponseEntity`.
- **Code Demo**: Product REST management API with JSON byte stream serialization via Jackson.

### [03. Persistence Layer JPA](./03_persistence_layer_jpa) (`03_persistence_layer_jpa`)
- **Focus**: Connecting to databases via Object-Relational Mapping (ORM).
- **Key Concepts**: JPA specification vs. Hibernate implementation, `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `JpaRepository`, H2 Database, Entity Lifecycles (Transient, Managed, Detached, Removed), JPQL queries.
- **Code Demo**: Entity creation, auto DDL execution, derived query methods, and custom JPQL queries.

### [04. Service Layer](./04_service_layer) (`04_service_layer`)
- **Focus**: Business logic encapsulation & transaction boundaries.
- **Key Concepts**: Controller-Service-Repository multi-tier architecture, `@Transactional` AOP proxies, `ThreadLocal` database connections, ACID guarantees, Constructor Dependency Injection.
- **Code Demo**: `OrderService` with transactional order placement, stock verification, and read-only transaction hints.

### [05. Input Validation](./05_input_validation) (`05_input_validation`)
- **Focus**: Defensive boundary protection using Jakarta Bean Validation.
- **Key Concepts**: Hibernate Validator, `@Valid`, `@NotBlank`, `@Email`, `@Size`, `@Min`, `@Max`, `@Pattern`, custom annotations (`@ValidPhoneNumber`), `ConstraintValidator`.
- **Code Demo**: Defensive user registration payload validation preventing malformed data from reaching domain services.

### [06. Exception Handling](./06_exception_handling) (`06_exception_handling`)
- **Focus**: Centralized global error handling and standard RFC 7807 error responses.
- **Key Concepts**: `@RestControllerAdvice`, `@ExceptionHandler`, `HandlerExceptionResolver`, preventing stack trace leakage, mapping domain exceptions to HTTP status codes.
- **Code Demo**: Intercepting `ResourceNotFoundException`, `BusinessRuleException`, and validation errors into uniform `ErrorResponse` payloads.

### [07. Response Transformation](./07_response_transformation) (`07_response_transformation`)
- **Focus**: Standardizing successful API responses & entity decoupling.
- **Key Concepts**: DTO pattern (Entity vs. DTO separation), circular reference prevention, `ApiResponse<T>` envelope pattern, `ResponseBodyAdvice<T>` automatic response wrapping.
- **Code Demo**: Global advice automatically wrapping controller return types into standardized JSON envelopes.

---

## 🛠️ How to Compile & Run

### Compile All Week 2 Projects
From the repository root:
```bash
mvn clean compile
```

### Run Any Specific Module
```bash
# Run Module 1: MVC Architecture
mvn -pl week2/01_mvc_architecture spring-boot:run

# Run Module 2: Presentation Layer
mvn -pl week2/02_presentation_layer spring-boot:run

# Run Module 3: Persistence Layer JPA
mvn -pl week2/03_persistence_layer_jpa spring-boot:run

# Run Module 4: Service Layer
mvn -pl week2/04_service_layer spring-boot:run

# Run Module 5: Input Validation
mvn -pl week2/05_input_validation spring-boot:run

# Run Module 6: Exception Handling
mvn -pl week2/06_exception_handling spring-boot:run

# Run Module 7: Response Transformation
mvn -pl week2/07_response_transformation spring-boot:run
```
