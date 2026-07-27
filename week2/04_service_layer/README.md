# Topic 04: Service Layer & Transaction Management (`@Transactional`)

## The First Principle: Separation of Concerns & Business Logic Boundaries

The **Service Layer** sits between the **Presentation Layer (Controllers)** and the **Persistence Layer (Repositories)**. 

### Why Controllers MUST NOT Contain Business Logic:
1. **Coupling**: If business logic (e.g., calculating tax, checking user permissions, reducing inventory stock) is embedded inside `@RestController` methods, it cannot be reused across other entry points (e.g., CLI runners, message queue consumers, scheduled background jobs).
2. **Transaction Isolation**: Controllers should execute fast and not hold database transactions open while awaiting HTTP network operations or serialization.
3. **Testability**: Services can be unit-tested without initializing Spring Web context or HTTP mocks.

---

## Transaction Mechanics & `@Transactional` AOP Proxies

Spring's `@Transactional` annotation uses **Aspect-Oriented Programming (AOP)** proxies to automate database transaction demarcation (`BEGIN`, `COMMIT`, `ROLLBACK`).

```text
==========================================================================================
                          SPRING @TRANSACTIONAL AOP PROXY FLOW
==========================================================================================

   Caller (Controller) 
          |
          v
   [ SPRING AOP PROXY ] (Generated around OrderServiceImpl)
          |
          +-----> 1. Intercepts method call
          +-----> 2. Fetches Connection from DataSource
          +-----> 3. Binds Connection to current thread (ThreadLocal)
          +-----> 4. Issues SQL: "BEGIN TRANSACTION;"
          |
          v
   [ OrderServiceImpl.placeOrder() ] (Actual Core Business Logic)
          |
          +-----> 5. Executes SQL queries via OrderRepository
          |
          +-----> 6. If method completes successfully:
          |          --> Issue SQL: "COMMIT;"
          |
          +-----> 7. If RuntimeException is thrown:
                     --> Issue SQL: "ROLLBACK;"
==========================================================================================
```

---

## Transaction Isolation & Propagation Modes

- `@Transactional(readOnly = true)`: Hints to Hibernate and database drivers that no entity modifications will occur. Enables performance optimizations (bypasses Hibernate dirty-checking snapshot creation).
- **Default Rollback Rule**: Spring `@Transactional` automatically triggers a database **ROLLBACK** for unchecked exceptions (`RuntimeException` and `Error`), but **COMMITS** for checked exceptions unless `rollbackFor = Exception.class` is explicitly declared.

---

## Constructor Injection Pattern (Reinforced from Week 1)

```java
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    // Constructor Dependency Injection ensures immutable fields and easy unit testing with Mocks!
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
```
