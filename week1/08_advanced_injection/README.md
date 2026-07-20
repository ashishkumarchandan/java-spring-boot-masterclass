# Topic 8: Advanced Injection Patterns (Collections & Optionals)

## The First Principle: Registry Scanning for Polymorphism

Spring's IoC Container excels at collecting multiple candidates dynamically from the bean registry. When you write `@Autowired List<MyPlugin> plugins;`, Spring does not look for a bean of type `List`. Instead, it does the following at the registry level:

1.  **Resolve Candidate Array**: It queries the `DefaultListableBeanFactory` for all bean definition names matching the generic type `MyPlugin`.
2.  **Order Selection**: It instantiates and processes all matching beans. If the beans implement `org.springframework.core.Ordered` or carry the `@Order` annotation, Spring sorts them based on their designated rank values.
3.  **Construct Injected Collection**: It creates a standard Java `ArrayList` (or `LinkedHashMap` for Map-based injection mapping bean names to bean instances), populates it with the sorted references, and injects it into the target class.

```text
  Registry:
  - "pluginA" -> PluginAInstance (Order = 10)
  - "pluginB" -> PluginBInstance (Order = 5)
  
  Injected List:
  [ PluginBInstance, PluginAInstance ]
```

---

## Why-Not-Just-What: Replacing Static Factories with Strategy Pattern DI

Why should we avoid hardcoded static factories and use collection injection instead?

### The Traditional Anti-Pattern:
```java
public class PaymentFactory {
    public static PaymentGateway getGateway(String type) {
        if ("stripe".equalsIgnoreCase(type)) return new StripeGateway();
        if ("paypal".equalsIgnoreCase(type)) return new PayPalGateway();
        throw new IllegalArgumentException("Unknown gateway");
    }
}
```

### Why this breaks down:
1.  **Violating Open/Closed Principle**: Every time you add a new payment gateway (e.g., ApplePay), you must edit `PaymentFactory.java` and modify the conditional switch block.
2.  **Bypassing IoC Container**: The gateways returned by the factory are created using `new`, meaning they are not Spring beans. You cannot autowire database services or settings inside `StripeGateway` because they are outside Spring's scope.

### The Spring Solution: Collection Autowiring
By autowiring a `Map<String, PaymentGateway>`, Spring automatically harvests all implementations. Adding a new gateway bean instantly updates the map without changing any existing code, maintaining true loose coupling.

### Handling Missing Dependencies safely with `Optional`:
If a dependency is optional (e.g., a custom logger that may or may not be defined depending on deployment environments), traditional `@Autowired` will throw a `NoSuchBeanDefinitionException` if the bean is missing. Wrapping the target type in `Optional<MyCustomLogger>` allows the application to bootstrap safely, substituting a default fallback behavior if the bean is missing.
