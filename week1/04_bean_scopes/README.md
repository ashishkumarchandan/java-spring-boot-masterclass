# Topic 4: Bean Scopes (Singleton, Prototype, and Mixed Scope Traps)

## The First Principle: Cached Registry vs. Dynamic Factory instantiation

Understanding bean scopes requires looking at Spring's internal bean cache maps at the JVM level:

1.  **Singleton Scope (Default)**:
    *   Spring maintains a `singletonObjects` Map (`ConcurrentHashMap<String, Object>`).
    *   When the context starts, Spring creates and registers all singleton beans.
    *   Subsequent requests for the bean return the *exact same memory reference* from the `singletonObjects` cache.
    *   **Destruction**: Spring manages the full lifecycle, including calling pre-destroy hooks when the container shuts down.

2.  **Prototype Scope**:
    *   Spring **does not** cache prototype instances. The `singletonObjects` cache map is bypassed.
    *   Every time you retrieve a prototype bean, the container runs the constructor and initialization pipeline, then hands the reference to the caller.
    *   **The Prototype Garbage Collection Trap**: Once the prototype bean is initialized and returned, Spring discards its reference. The caller owns the bean. Because Spring does not hold a reference, **Spring does not call the destruction methods (`@PreDestroy` or `DisposableBean.destroy()`) of prototype beans**. If your prototype bean allocates system resources (e.g., file descriptors, sockets), you must close them manually, or you will cause resource leaks.

---

## Why-Not-Just-What: The Mixed Scope Injection Trap

What happens when a Singleton bean depends on a Prototype bean?

### The Trap:
1.  Since the Singleton bean is created only once, its properties are populated only once during bootstrap.
2.  Spring fetches a prototype instance *once* to autowire into the Singleton's field.
3.  For the rest of the application's runtime, the Singleton bean references that exact same prototype instance. 
4.  The prototype bean is effectively "frozen" inside the singleton, defeating the purpose of prototype scope (which is to get a fresh instance on every call).

### The Solution:
To bypass this freeze, we must delay the resolution of the prototype bean. Instead of direct injection, we can use:
*   **`ObjectFactory<T>`**: Injects a factory that calls `context.getBean(T.class)` on demand.
*   **Method Injection (`@Lookup`)**: Instructs Spring to override a method in the singleton bean at runtime (using CGLIB subclassing) to query the container for the prototype bean.

---

## The Mixed Scope Trap and Lookup Solution Flow

```text
==========================================================================================
                     THE MIXED SCOPE TRAP & OBJECTFACTORY RESOLUTION
==========================================================================================

   [ DIRECT INJECTION TRAP ]
   
   SingletonBean (Created ONCE at startup)
     |
     +--> Injected Field: PrototypeBeanRef (Identity Hash: #101)
     |
     +--> Call: usePrototype() ---> Accesses PrototypeBeanRef (#101)
     +--> Call: usePrototype() ---> Accesses PrototypeBeanRef (#101) (Always the same!)

------------------------------------------------------------------------------------------

   [ DYNAMIC RESOLUTION (ObjectFactory/Lookup) ]
   
   SingletonBean (Created ONCE at startup)
     |
     +--> Injected Field: ObjectFactory<PrototypeBean>
     |
     +--> Call: usePrototype() 
            |
            |---> factory.getObject() 
            |       |
            |       +---> Calls ApplicationContext.getBean("prototypeBean")
            |       +---> Creates NEW PrototypeBean (Identity Hash: #202) -> return
            |
     +--> Call: usePrototype()
            |
            |---> factory.getObject()
            |       |
            |       +---> Calls ApplicationContext.getBean("prototypeBean")
            |       +---> Creates NEW PrototypeBean (Identity Hash: #303) -> return
==========================================================================================
```
