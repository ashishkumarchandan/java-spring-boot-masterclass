# Topic 5: The Tight Coupling Problem & Inversion of Control (IoC)

## The First Principle: Dependency Inversion at the JVM / Class Level

In traditional software design, classes actively locate and instantiate their dependencies. At the compiled bytecode level, if class `Car` instantiates class `V8Engine` using `new V8Engine()`, the Java Compiler outputs a direct symbol ref to `V8Engine` in `Car.class`.

```text
  [ Compile-time Class Dependency ]
  Car.class ==============> V8Engine.class  (Tightly Coupled)
```

### The Architectural Problem:
This creates **Tight Coupling**:
1.  **Lifetime Control**: `Car` dictates the lifetime of `V8Engine`. The engine cannot exist outside of the context of a car instance.
2.  **No Abstraction**: You cannot replace `V8Engine` with an `ElectricEngine` without editing and recompiling the `Car` class.
3.  **Compilation Bottleneck**: A change in `V8Engine.java` forces recompilation of `Car.java`, leading to fragile builds in large systems.

**Inversion of Control (IoC)** reverses this control flow. The `Car` class defines its dependency as an abstract contract (an `Engine` interface). The responsibility of creating the concrete engine and giving it to the car is shifted (inverted) to an external assembler (the IoC Container).

```text
  [ IoC Dependency Inversion ]
  Car.class ==============> [ <<interface>> Engine ] <============== ElectricEngine.class
                                                                      (Loosely Coupled)
```

---

## Why-Not-Just-What: Building a Manual IoC Container

How does Spring actually do this under the hood? It is simply a JVM Registry that maps identifiers to objects and resolves dependencies reflectively. To understand this from first principles, we will build a simplified, manual IoC container from scratch in Java:

1.  **Registration**: A map of classes to instances (`Map<Class<?>, Object>`).
2.  **Inspection**: Using Reflection to scan fields annotated with a custom `@Inject` annotation.
3.  **Resolution**: Automatically fetching the required dependency from the registry and setting the field.

By building this, we demystify Spring's `@Autowired` mechanism and see that it is just metadata configuration and reflective field modification.

---

## Tight Coupling vs. Loose Coupling Memory Model

```text
==========================================================================================
                     TIGHT COUPLING VS LOOSE COUPLING IN MEMORY
==========================================================================================

   [ TIGHTLY COUPLED SYSTEM ]
   (No separation of concerns)
   
      JVM Heap
      +-----------------------------------------+
      |  Car Object                             |
      |  - engineRef ---------------------+     |
      |                                   |     |
      +-----------------------------------|-----+
                                          v
                                 +------------------+
                                 | V8Engine Object  |
                                 +------------------+
   (Car explicitly instantiated V8Engine. Cannot be swapped or mocked at runtime.)

------------------------------------------------------------------------------------------

   [ LOOSELY COUPLED SYSTEM (IoC Container managed) ]
   
      JVM Heap
      +-------------------------+                 +--------------------------------------+
      |  Car Object             |                 |  IoC Container Registry (Map)        |
      |                         |                 |  - Engine.class ---> [ElectricEngine]|
      |  - engineRef (Interface)|<========\       +--------------------------------------+
      +-------------------------+         |
                                          |
                                          \=========> +-----------------------+
                                                      | ElectricEngine Object |
                                                      +-----------------------+
   (Car depends only on the Engine Interface. IoC Container injects the ElectricEngine 
    instance at runtime. Car is unaware of the concrete implementation class.)
==========================================================================================
```
