# Week 1: Spring Core & Spring Boot Bootstrap

This week focuses on building a deep, conceptual understanding of the core mechanisms that power the Spring Framework and Spring Boot. Instead of looking at Spring as "magic," these 12 modules break down the core container, bean instantiation, lifecycles, scopes, dependency resolution, and boot-up procedures.

---

## 📂 Module Reference Guide

Here is a guide to the 12 sub-projects in this folder. Each is a fully functional Spring Boot application configured as a standard Maven project.

### 01. Core Container (`01_core_container`)
- **Focus**: Understanding how the Inversion of Control (IoC) Container holds references.
- **Key Concepts**: Application Context, `@Component` scanning, bean wiring.
- **Console Output**: Traces identity hashes of components to demonstrate that Spring reuses singletons across layers.

### 02. Bean Mechanics (`02_bean_mechanics`)
- **Focus**: Comparing different bean registration strategies.
- **Key Concepts**: `@Component` (automatic detection) vs. `@Configuration` + `@Bean` (explicit registration for external dependencies).
- **Console Output**: Shows when beans are instantiated and how external simulated libraries are injected.

### 03. Bean Lifecycle (`03_bean_lifecycle`)
- **Focus**: Tracing the initialization and destruction phases of Spring beans.
- **Key Concepts**: Lifecycle hooks, `@PostConstruct`, `@PreDestroy`.
- **Console Output**: Logs the step-by-step container startup and shutdown sequence to see when hooks fire relative to application lifecycle.

### 04. Bean Scopes (`04_bean_scopes`)
- **Focus**: The *Mixed-Scope Trap* and how to resolve it.
- **Key Concepts**: Singleton scope (default) vs. Prototype scope.
- **Console Output**: Demonstrates why injecting a Prototype bean directly into a Singleton freezes the prototype's state, and how using `ObjectFactory<T>` resolves this on-demand.

### 05. Coupling & IoC (`05_coupling_and_ioc`)
- **Focus**: Designing decoupled systems.
- **Key Concepts**: Dependency Inversion Principle, programming to interfaces instead of concrete classes.
- **Console Output**: Traces how changes in underlying service implementations can be hot-swapped dynamically without modifying the consuming class.

### 06. Dependency Injection Types (`06_dependency_injection_types`)
- **Focus**: Comparing DI patterns.
- **Key Concepts**: Constructor Injection (standard/recommended) vs. Setter Injection (optional components).
- **Console Output**: Demonstrates order of initialization and discusses why Field Injection (`@Autowired` on variables) is avoided in modern codebases.

### 07. Dependency Resolution (`07_dependency_resolution`)
- **Focus**: Handling multiple beans of the same type.
- **Key Concepts**: `@Primary` vs. `@Qualifier` annotations.
- **Console Output**: Demonstrates how Spring decides which bean to inject when multiple implementations of an interface exist.

### 08. Advanced Injection (`08_advanced_injection`)
- **Focus**: Strategy Pattern automation.
- **Key Concepts**: Multi-bean injection into a `List<BeanType>` or `Map<String, BeanType>`.
- **Console Output**: Runs dynamic task routing based on bean names/types.

### 09. Framework vs. Boot (`09_framework_vs_boot`)
- **Focus**: Appreciating Spring Boot's automation.
- **Key Concepts**: Manual Spring wiring vs. Spring Boot's Auto-configuration and Starters.
- **Console Output**: Compares explicit manual bean setups to zero-config Boot.

### 10. Maven Ecosystem (`10_maven_ecosystem`)
- **Focus**: Maven lifecycle and dependency tree.
- **Key Concepts**: Coordinates (Group ID, Artifact ID, Version), scopes, parent-POM inheritance.

### 11. Autoconfigure & Conditional (`11_autoconfigure_conditional`)
- **Focus**: Conditional bean registration.
- **Key Concepts**: `@ConditionalOnProperty`, `@ConditionalOnClass`, feature flags.
- **Console Output**: Activates simulated local payment gateways or production gateways based on application property flags.

### 12. Spring Boot Bootstrap (`12_springboot_bootstrap`)
- **Focus**: The internal mechanics of `SpringApplication.run()`.
- **Key Concepts**: Application listeners, environment setup, context refresh, Banner printing, runner execution.
- **Console Output**: Logs the deep startup stages of Spring Boot.

---

## 🛠️ How to Compile & Run

To run any module, navigate to the specific module directory and execute:
```bash
mvn spring-boot:run
```
Alternatively, import the parent directory in IntelliJ IDEA or VS Code and run the `MainApplication` classes.
