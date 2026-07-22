# Topic 2: Bean Declaration Mechanics (@Component vs @Bean)

## The First Principle: Bytecode Scanning vs. Reflection-Based Registration

At the JVM level, Spring has two primary ways to discover and register beans:

1.  **Implicit Scanning (`@Component` / `@Service` / `@Repository`)**:
    *   During bootstrap, Spring utilizes a classpath scanner (e.g., `ClassPathBeanDefinitionScanner`).
    *   It uses a low-level bytecode reader (using the ASM library) to parse `.class` files in the specified packages *without* loading them into the JVM ClassLoader first. This saves memory and prevents premature class loading.
    *   If it detects the `@Component` annotation (or its stereotypes), it registers a `BeanDefinition` mapping the class to the container.
2.  **Explicit Registration (`@Configuration` + `@Bean`)**:
    *   Spring loads the class annotated with `@Configuration`.
    *   It uses Java Reflection (`Class.getDeclaredMethods()`) to extract all methods annotated with `@Bean`.
    *   For each method, it creates a `ConfigurationClassBeanDefinition` containing metadata about the method name, return type, and parameter dependencies.

### CGLIB Subclass Proxying:
To guarantee that Spring beans remain singletons even when configuration methods call each other, Spring uses **CGLIB (Code Generation Library)** at bytecode generation time.
*   When a class is annotated with `@Configuration`, Spring wraps it in a dynamically generated subclass (using bytecode instrumentation).
*   This CGLIB-enhanced proxy intercepts all calls to `@Bean` methods.
*   If you call `databaseConnection()` from another `@Bean` method, the proxy checks the `ApplicationContext` registry first. If the bean already exists, it returns the cached bean instead of executing the method body again.

---

## Why-Not-Just-What: The CGLIB Lite-Mode Trap

Why do we need both, and what happens when we mix them incorrectly?

### Why we need both:
*   Use `@Component` for code you own. It is low-boilerplate and self-documenting.
*   Use `@Configuration` + `@Bean` when:
    *   You are configuring classes from third-party libraries (e.g., configuring a `dataSource` from `hikari-db` which you cannot edit to add `@Component`).
    *   You need complex, parameterized instantiation logic.

### The "Lite Mode" Trap:
If you place a `@Bean` method inside a regular class annotated with `@Component` instead of `@Configuration`, Spring executes this in **"Lite Mode"**.
*   In Lite Mode, Spring **does not** generate a CGLIB proxy subclass.
*   If you call a `@Bean` method from another `@Bean` method inside a `@Component` class, it behaves like a standard Java method call. It runs the constructor again, instantiating a completely new object, bypassing the container, and creating multiple duplicate instances of what was supposed to be a Singleton bean!

---

## CGLIB Configuration Proxy Flow

```text
==========================================================================================
                     @Configuration (Full Mode) vs @Component (Lite Mode)
==========================================================================================

   [ FULL MODE: @Configuration ]
   AppConfigClass$$EnhancerBySpringCGLIB
     |
     +--> Call to dataSource() 
            |
            |--> Intercepted by CGLIB Callback
            |--> Check Container Registry: "Does 'dataSource' exist?"
            |       |
            |       +--> YES: Return existing cached instance from Heap.
            |       +--> NO:  Super.dataSource() (run actual method code) -> cache -> return.

------------------------------------------------------------------------------------------

   [ LITE MODE: @Component ]
   AppConfigComponent (No CGLIB wrapper)
     |
     +--> Call to dataSource()
            |
            |--> Plain Java method call executed directly on Thread Stack
            |--> Constructor runs again (`new DataSource()`)
            |--> Returns a NEW, unmanaged instance to the caller. Singleton broken!
==========================================================================================
```
