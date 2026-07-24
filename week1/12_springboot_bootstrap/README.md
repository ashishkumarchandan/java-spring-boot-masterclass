# Topic 12: Spring Boot Internal Bootstrapping Flow (Step-by-Step)

## The First Principle: Programmatic Application Bootstrapping

At the JVM layer, a Spring Boot application starts execution like any standard Java application—from the static `main(String[] args)` method. Calling `SpringApplication.run(MyClass.class, args)` initiates a sequential bootstrapping pipeline.

Unlike traditional containers where the container manages the JVM, Spring Boot is a **self-bootstrapping container**. It executes programmatic steps to spin up the container environment, scan sources, compile the configuration registry, refresh singletons, and launch listeners:

```text
+-----------------------------------------------------------------------+
|                 SPRING BOOT RUNTIME BOOTSTRAP TIMELINE                |
+-----------------------------------------------------------------------+
|                                                                       |
|  1. SpringApplication Initialization                                  |
|     - Deduce web application type (SERVLET, REACTIVE, or NONE).       |
|     - Load all `ApplicationContextInitializer` and `ApplicationListener` |
|       instances from classpath metadata (spring.factories).           |
|                                                                       |
|  2. Start Run Listeners                                               |
|     - Broadcast "Starting" event to early listeners.                 |
|                                                                       |
|  3. Prepare Environment                                               |
|     - Create `ConfigurableEnvironment` (combines JVM variables, system|
|       environment variables, and command-line arguments).             |
|     - Broadcast "Environment Prepared" event (triggers loading of     |
|       `application.properties` or `.yml` configuration files).        |
|                                                                       |
|  4. Print Banner                                                      |
|     - Outputs default or custom ASCII art banner to console.          |
|                                                                       |
|  5. Create ApplicationContext                                         |
|     - Instantiates context reflectively matching deduced type.        |
|                                                                       |
|  6. Prepare Context                                                   |
|     - Invokes custom `ApplicationContextInitializer` hook classes.    |
|     - Broadcasts "Context Prepared" event.                            |
|                                                                       |
|  7. Load Bean Definitions                                             |
|     - Scans the primary source package to load initial bean definitions.|
|     - Broadcasts "Context Loaded" event.                              |
|                                                                       |
|  8. Refresh ApplicationContext                                        |
|     - Performs standard IoC initialization (constructs singletons).    |
|     - Starts Embedded Web Server (e.g. Tomcat) if Web application.    |
|                                                                       |
|  9. Broadcast "Started" & "Ready" Events                              |
|     - Triggers active lifecycle listeners.                            |
|                                                                       |
| 10. Execute Runners                                                   |
|     - Scans context for `CommandLineRunner` / `ApplicationRunner` beans|
|       and executes their run methods.                                 |
|                                                                       |
+-----------------------------------------------------------------------+
```

---

## Why-Not-Just-What: Modifying Context Pre-Refresh

Why does Spring Boot provide hooks like `ApplicationContextInitializer` and `CommandLineRunner`?

### 1. The Pre-Refresh Phase (`ApplicationContextInitializer`):
At startup, you might need to fetch configuration parameters (like database credentials) from an external vault service (e.g. HashiCorp Vault or AWS Secrets Manager) and inject them as properties before Spring starts parsing bean configuration files.
*   If you wait for beans to instantiate to fetch these values, it is too late—Spring will fail to construct database beans because configuration properties are missing.
*   Using `ApplicationContextInitializer` lets you intercept the context *before* the refresh phase, download secrets, and inject them into Spring's environment dynamically.

### 2. Post-Startup Actions (`CommandLineRunner` / `ApplicationRunner`):
Once the context refresh phase is complete and the embedded Tomcat server is listening, you might need to run startup scripts (e.g. pre-warming caches, seeding reference databases, or verifying message queue connections).
*   If you write this logic in `@PostConstruct`, it executes *during* the refresh phase. If your startup logic blocks or hangs, the server never finishes booting, and health checks will fail.
*   `CommandLineRunner` executes *after* the application context has fully loaded and the application is declared ready, preventing bootstrap blockers.
