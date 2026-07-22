# Topic 3: The Bean Lifecycle Pipeline (Instantiation to Destruction)

## The First Principle: Separating Instantiation from Ready-State

At the JVM level, an object's construction via constructor executes before fields can be populated by outside forces. If Class `A` depends on Class `B` and uses field injection (`@Autowired private B b;`), the sequence of events is:
1.  **Instantiation**: The JVM executes `new A()`. At this split second on the heap, the field `b` is `null`.
2.  **Property Population**: Only *after* the constructor completes does Spring's IoC container use reflection (`Field.set()`) to populate `b` with the reference to `B`.

### The Lifecycle Problem:
If you attempt to interact with the database or configure an internal state using dependency `b` inside the *constructor* of `A`, you will encounter a `NullPointerException`. 
The JVM requires the constructor to complete first, meaning object **instantiation** and object **initialization** (making it ready to serve requests) must be separated.

Spring solves this by providing a multi-phase **Lifecycle Pipeline**:

```text
+-----------------------------------------------------------------------+
|                        THE BEAN LIFECYCLE PIPELINE                    |
+-----------------------------------------------------------------------+
|                                                                       |
|   1. Class Loading & BeanDefinition Registration                      |
|      (Reads metadata, prepares blueprints)                            |
|                                                                       |
|   2. Instantiation (Constructor execution)                            |
|      (Memory allocated, instance variables defaults set, fields null) |
|                                                                       |
|   3. Populate Properties (Dependency Injection)                       |
|      (Spring autowires dependencies via setters/fields)               |
|                                                                       |
|   4. BeanNameAware / BeanFactoryAware / ApplicationContextAware       |
|      (Injects container-level references)                             |
|                                                                       |
|   5. BeanPostProcessor: postProcessBeforeInitialization               |
|      (Intercepts bean; handles `@PostConstruct` annotation)           |
|                                                                       |
|   6. Initialization Callbacks:                                        |
|      - InitializingBean.afterPropertiesSet()                          |
|      - Custom initMethod()                                            |
|                                                                       |
|   7. BeanPostProcessor: postProcessAfterInitialization               |
|      (Wraps bean in dynamic AOP Proxies if transactions/security apply)|
|                                                                       |
|   8. Bean is READY FOR USE                                            |
|                                                                       |
|   9. Container Shutdown begins                                        |
|                                                                       |
|  10. Destruction Callbacks:                                           |
|      - `@PreDestroy` method called                                    |
|      - DisposableBean.destroy() called                                |
|      - Custom destroyMethod() called                                  |
|                                                                       |
+-----------------------------------------------------------------------+
```

---

## Why-Not-Just-What: Why Constructors Are Insufficient

Why can't we simply write initialization code in a constructor or destruction code in standard finalizers?

### Why Constructors Fail for Container Managed Beans:
1.  **Dependencies are not yet injected**: Accessing fields injected via `@Autowired` or `@Value` inside a constructor results in a `NullPointerException`.
2.  **Lack of Proxying**: If the bean requires AOP (Aspect-Oriented Programming) wrappers (e.g., `@Transactional` interceptors), the object must be initialized and returned from `postProcessAfterInitialization` as a proxy wrapper. A constructor can only return the raw, un-proxied instance of the class itself.

### Why Java Finalizers Fail:
Java's `finalize()` method is deprecated, slow, and unreliable. There is no guarantee *when* or *if* the garbage collector runs it. System resources (such as file handles, database connections, and sockets) must be closed deterministically during container shutdown using `@PreDestroy` or `DisposableBean#destroy`.
