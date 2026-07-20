# Topic 6: Types of Dependency Injection (Constructor, Setter, Field)

## The First Principle: Compiler Guarantees & JVM Access Control

How Spring injects references into fields determines your code's compile-time safety and run-time immutability:

1.  **Field Injection (`@Autowired private Service service;`)**:
    *   **Under the Hood**: Spring instantiates the bean and uses Java Reflection (`field.setAccessible(true)` followed by `field.set(beanInstance, dependencyInstance)`) to write directly to the private class field.
    *   **JVM Implication**: The field *cannot* be marked `final`. Since the injection happens post-instantiation, the compiler cannot guarantee the field is never reassigned.
    
2.  **Setter Injection**:
    *   **Under the Hood**: Spring instantiates the bean, then uses reflection to call setter methods (`setService(Service s)`).
    *   **JVM Implication**: The field *cannot* be marked `final`. It is ideal for optional or mutable dependencies that can change at runtime.

3.  **Constructor Injection**:
    *   **Under the Hood**: Spring calls the constructor, passing the dependencies as arguments.
    *   **JVM Implication**: The fields **can** be marked `final`. The Java Compiler guarantees that the fields are initialized during constructor execution and can never be reassigned. This ensures thread-safety and runtime immutability.

---

## Why-Not-Just-What: The Field Injection Anti-Pattern & Circular Dependency resolution

Why is Constructor Injection the modern standard, and why is Field Injection discouraged?

### The Problems with Field Injection:
1.  **Hidden Dependencies**: A class using field injection hides its dependencies from the public interface. You cannot see what the class needs without inspecting its private fields.
2.  **Harder Unit Testing**: You cannot instantiate the class in a plain unit test (`new Service()`) without starting a heavy Spring test runner or manually using reflection (`ReflectionTestUtils`) to inject mocks.
3.  **Null Safety Risk**: The compiler does not enforce initialization, risking runtime `NullPointerException`s if the class is instantiated manually.

### The Circular Dependency Trap:
A circular dependency occurs when Class A depends on Class B, and Class B depends on Class A.
*   **Constructor Injection Failure**: If both classes use constructor injection, Spring cannot instantiate either. To instantiate `A`, it needs a fully constructed `B`. To instantiate `B`, it needs a fully constructed `A`. This triggers a `BeanCurrentlyInCreationException` and crashes startup.
*   **Setter/Field Injection Resolution**: If they use setter/field injection, Spring resolves this using its internal **3-stage Cache** (`singletonFactories`). It instantiates `A` (fields null), registers an early reference (ObjectFactory) for `A` in the cache, instantiates `B`, injects the early reference of `A` into `B`, and then injects the now-fully-constructed `B` back into `A`.

*Note: While Spring can resolve this, circular dependencies indicate bad architectural design (tight coupling) and should be resolved using interfaces or event-driven models.*

---

## Dependency Injection JVM Flows

```text
==========================================================================================
                      CONSTRUCTOR VS FIELD INJECTION LIFECYCLE
==========================================================================================

   [ CONSTRUCTOR INJECTION ] (Atomic, Safe, Immutable)
   
   Class A (Autowired Constructor)
     |
     |---> Call Constructor: new A(Dependency B)
     |       |
     |       +---> Injected fields assigned to final storage *during* instantiation.
     |       +---> Object A is created in a FULLY initialized, ready state.
     |
     +---> Result: Immutability guaranteed. No null references possible.

------------------------------------------------------------------------------------------

   [ FIELD INJECTION ] (Multi-step, Mutable, Risky)
   
   Class A (Autowired Field)
     |
     |---> Step 1: Call Default Constructor: new A() (Fields are null)
     |             (Object A sits on heap with uninitialized field 'b' = null)
     |
     |---> Step 2: Spring Container uses Reflection:
     |             Field bField = A.class.getDeclaredField("b");
     |             bField.setAccessible(true);
     |             bField.set(instanceA, instanceB);
     |
     +---> Result: Object A is now populated. Fields can be mutated later; testing is hard.
==========================================================================================
```
