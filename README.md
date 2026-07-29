# 🎓 18-Week Java & Spring Boot Masterclass

Welcome to the **18-Week Java & Spring Boot Masterclass** repository! This repository tracks my journey from core Java and Spring fundamentals to building enterprise-grade, production-ready microservices and cloud applications.

---

## 📅 Course Roadmap & Progress Dashboard

This table tracks the syllabus and my learning progress week-by-week.

| Week | Focus / Domain | Key Topics Covered | Status | Projects / Sub-modules |
| :--- | :--- | :--- | :---: | :--- |
| **Week 1** | **Spring Core & Spring Boot Bootstrap** | IoC Container, Bean Lifecycle, Scopes, Dependency Injection, Maven, Autoconfiguration | 🟢 Completed | [Go to Week 1](./week1/) (12 Sub-projects) |
| **Week 2** | **Building the Web Application** | MVC Architecture, Presentation Layer, JPA Persistence, Service Layer, Validation, Exception Handling, Response Envelopes | 🟢 Completed | [Go to Week 2](./week2/) (7 Sub-projects) |
| **Week 3** | *REST APIs & Validation* | Controller advice, Validation annotations, DTO pattern, Content negotiation | ⚪ Planned | *TBD* |
| **Week 4** | *Spring Security & OAuth2* | Authentication, Authorization, JWT, Role-Based Access Control | ⚪ Planned | *TBD* |
| **Week 5** | *Testing (Unit, Integration, Slice)* | JUnit 5, Mockito, `@SpringBootTest`, `@WebMvcTest`, Testcontainers | ⚪ Planned | *TBD* |
| **Week 6** | *Microservices: Service Registry* | Spring Cloud Netflix Eureka, Service Discovery, Load Balancing | ⚪ Planned | *TBD* |
| **Week 7** | *Microservices: Gateway & Config* | Spring Cloud Gateway, Routing, Centralized Config Server | ⚪ Planned | *TBD* |
| **Week 8** | *Asynchronous Processing* | Spring `@Async`, Event Listeners, Task Scheduling, Thread Pools | ⚪ Planned | *TBD* |
| **Week 9** | *Message Brokers (RabbitMQ)* | AMQP, Producer-Consumer pattern, Exchange types, DLQs | ⚪ Planned | *TBD* |
| **Week 10** | *Message Brokers (Apache Kafka)* | Topics, Partitions, Consumer groups, Event sourcing basics | ⚪ Planned | *TBD* |
| **Week 11** | *Caching Strategies* | Redis integration, `@Cacheable`, Cache eviction, Write-through/Behind | ⚪ Planned | *TBD* |
| **Week 12** | *API Documentation & OpenAPI* | Swagger/OpenAPI 3, Redoc, API versioning, contract testing | ⚪ Planned | *TBD* |
| **Week 13** | *Observability & Monitoring* | Micrometer, Prometheus, Grafana dashboards, Spring Boot Actuator | ⚪ Planned | *TBD* |
| **Week 14** | *Distributed Tracing* | Micrometer Tracing (Zipkin/Brave), Sleuth, Log correlation | ⚪ Planned | *TBD* |
| **Week 15** | *Containerization (Docker)* | Dockerfiles, Docker Compose multi-container setups, Buildpacks | ⚪ Planned | *TBD* |
| **Week 16** | *Kubernetes Deployment* | Pods, Deployments, Services, ConfigMaps, Secrets, Ingress | ⚪ Planned | *TBD* |
| **Week 17** | *CI/CD Pipelines* | GitHub Actions, automated testing, SonarQube quality gates, Docker Hub push | ⚪ Planned | *TBD* |
| **Week 18** | *Capstone Project* | End-to-end distributed system implementation | ⚪ Planned | *TBD* |

---

## 📂 Project Structure & Naming Strategy

The project utilizes a nested structure designed for linear progression and standard enterprise layouts:
- **Folders are prefixed chronologically** (e.g., `01_core_container`, `02_bean_mechanics`) to preserve the logical order of topics in your IDE and GitHub.
- **Standard Maven Structure** is used across all modules. Every sub-project contains:
  - `src/main/java` – Source code grouped by feature packages (`com.platform.[topic]`).
  - `src/main/resources` – Configuration files (`application.properties`).
  - `pom.xml` – Explicit dependency management.

---

## 🛠️ Week 1: Spring Core & Boot Deep Dive

Here is the breakdown of the modules completed in **Week 1**:

1. **[01_core_container](./week1/01_core_container)**: Wire components across layers to see how the IoC container holds references.
2. **[02_bean_mechanics](./week1/02_bean_mechanics)**: Compare `@Component` for application beans vs. `@Configuration` / `@Bean` for external library simulation.
3. **[03_bean_lifecycle](./week1/03_bean_lifecycle)**: Hooks like `@PostConstruct` and `@PreDestroy` logging container startup and shutdown sequences.
4. **[04_bean_scopes](./week1/04_bean_scopes)**: Demonstrates the *Mixed-Scope Trap* (injecting prototype beans into a singleton) and the factory-based solution.
5. **[05_coupling_and_ioc](./week1/05_coupling_and_ioc)**: Showcases a decoupled architecture depending on interfaces instead of concrete classes.
6. **[06_dependency_injection_types](./week1/06_dependency_injection_types)**: Highlights constructor injection (recommended) vs. setter injection.
7. **[07_dependency_resolution](./week1/07_dependency_resolution)**: Resolving bean ambiguity using `@Qualifier` and `@Primary`.
8. **[08_advanced_injection](./week1/08_advanced_injection)**: Injecting lists or maps of strategy beans dynamically.
9. **[09_framework_vs_boot](./week1/09_framework_vs_boot)**: Comparing old Spring Framework XML/manual wiring against Spring Boot's autoconfiguration.
10. **[10_maven_ecosystem](./week1/10_maven_ecosystem)**: Working with sub-modules and coordinates.
11. **[11_autoconfigure_conditional](./week1/11_autoconfigure_conditional)**: Using `@ConditionalOnProperty` to toggle feature flags.
12. **[12_springboot_bootstrap](./week1/12_springboot_bootstrap)**: Tracing the Spring Boot internal lifecycle startup sequence.

---

## 🚀 How to Run the Projects

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- Maven (optional, or run directly within your IDE)

### Running via an IDE (Recommended)
1. Import the root folder in **IntelliJ IDEA**, **Eclipse**, or **VS Code**.
2. The IDE will automatically detect the parent `pom.xml` and import all 12 modules.
3. Open any sub-project's `MainApplication.java` and click **Run**.

### Running via Terminal
From the root directory:
```bash
# Build all sub-projects
mvn clean compile

# Run a specific sub-project (example: 04_bean_scopes)
mvn -pl week1/04_bean_scopes spring-boot:run
```
