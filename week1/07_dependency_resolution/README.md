# Topic 7: Dependency Resolution & Ambiguity Sorting (@Qualifier, @Primary)

## The First Principle: Resolution Decision Tree

When Spring autowires a field or constructor parameter, it performs resolution using a strict, step-by-step decision algorithm. At the container level, Spring's `DefaultListableBeanFactory#doResolveDependency` executes:

```text
               +------------------------------------------+
               |  Request to Resolve Dependency of Type T |
               +------------------------------------------+
                                    |
                                    v
                  +-----------------------------------+
                  |  Query bean registry for type T   |
                  +-----------------------------------+
                                    |
                                    v
                     /-----------------------------\
                    /   How many beans of type T    \
                    \         were found?           /
                     \-----------------------------/
                           /        |        \
            (Zero Candidates)   (One Bean)    (Multiple Beans)
                  /                 |                 \
                 v                  v                  v
    +-------------------------+ +----------+  /-------------------------\
    | NoSuchBeanDefinition-   | | Return   | / Does injection point have \
    | Exception thrown.       | | that bean| \  a @Qualifier annotation? /
    +-------------------------+ +----------+  \-------------------------/
                                                 /                     \
                                              (Yes)                    (No)
                                               /                         \
                                              v                           v
                                     +-----------------+      /---------------------\
                                     | Match qualifier |     /   Is there a bean     \
                                     | name. Return it |     \  marked with @Primary?/
                                     +-----------------+      \---------------------/
                                                                 /               \
                                                              (Yes)              (No)
                                                               /                   \
                                                              v                     v
                                                   +-------------+      /-----------------------\
                                                   | Return bean |     /  Does any bean name     \
                                                   | with @Primary|    \  match parameter/field? /
                                                   +-------------+      \-----------------------/
                                                                           /                 \
                                                                        (Yes)                (No)
                                                                         /                     \
                                                                        v                       v
                                                           +--------------------+ +-------------------------+
                                                           | Match name. Return | | NoUniqueBeanDefinition- |
                                                           | that instance.     | | Exception thrown.       |
                                                           +--------------------+ +-------------------------+
```

---

## Why-Not-Just-What: Global Defaults vs. Explicit Overrides

Why do we need both `@Primary` and `@Qualifier`?

### The Purpose of `@Primary` (Global Default):
*   Marks a bean as the default implementation when multiple matching beans are present.
*   Ideal for framework defaults. For example, if you have a production-grade database repository (`JpaRepository`) and a local file mock repository (`MockRepository`), you mark `JpaRepository` as `@Primary`. All developers who type `@Autowired private Repository repo;` will get the production repository automatically.

### The Purpose of `@Qualifier` (Explicit Selection):
*   Overrides any `@Primary` configuration.
*   Allows injection points to request specific beans. In the repository scenario above, a developer writing a test configuration class can write `@Autowired @Qualifier("mockRepository") private Repository repo;` to override the `@Primary` JPA bean.

### The Implicit Fallback: Parameter/Field Name Matching
If you do not specify `@Primary` or `@Qualifier`, Spring executes a final fallback logic: it compares the name of the Java variable/parameter to the bean identifiers registered in the container.
If your bean method is named `postgresService()` and your field is `private Service postgresService;`, Spring matches them by name. However, relying on name-matching is fragile because code refactoring tools (which rename variable names) can break the dependency injection at runtime. Use `@Qualifier` for explicit declaration.
