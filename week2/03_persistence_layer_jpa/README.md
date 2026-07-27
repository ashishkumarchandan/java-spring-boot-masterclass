# Topic 03: Persistence Layer, ORM & Spring Data JPA

## The First Principle: Object-Relational Impedance Mismatch

In Java memory, data is modeled as an **Object Graph** (memory addresses, references, inheritance, encapsulation). In relational databases (RDBMS), data is stored as **Tables & Tuples** (rows, columns, foreign keys, normalized data).

Translating between Java Heap memory and RDBMS tables manually requires writing raw JDBC code:
```java
// Traditional JDBC: High risk of SQL injection, manual ResultSet parsing, resource leak nightmare
PreparedStatement stmt = conn.prepareStatement("INSERT INTO products (name, price) VALUES (?, ?)");
stmt.setString(1, product.getName());
stmt.setBigDecimal(2, product.getPrice());
stmt.executeUpdate();
```

**Object-Relational Mapping (ORM)** bridges this gap. **JPA (Java Persistence API / Jakarta Persistence)** is the standard specification, and **Hibernate** is the underlying ORM engine implementation used by default in Spring Boot.

---

## JPA Entity Lifecycle States

Every Java object associated with a JPA `@Entity` exists in one of 4 lifecycle states inside the Hibernate `PersistenceContext` (L1 Cache):

```text
==========================================================================================
                               JPA ENTITY LIFECYCLE STATES
==========================================================================================

   [ NEW / TRANSIENT ]  -------->  em.persist() / repository.save()
   (In Heap memory,                 |
    no DB identity/PK)              v
                           [ MANAGED / PERSISTENT ] <------- em.find() / Query
                           (Tracked by L1 Cache,              |
                            auto dirty-checked on TX commit)  |
                                    |                         | em.detach() / TX Close
                                    | em.remove()             v
                                    v                 [ DETACHED ]
                             [ REMOVED ]              (Has PK, but no longer
                             (Scheduled for           tracked by PersistenceContext)
                              SQL DELETE)
==========================================================================================
```

1. **Transient**: Object created via `new MyEntity()`. Not associated with an `EntityManager` or database row.
2. **Managed**: Entity has a Primary Key and is actively managed in the `PersistenceContext` (L1 Cache). Any setters invoked on a Managed entity automatically generate SQL `UPDATE` statements on transaction commit without calling `save()`.
3. **Detached**: The transaction/session ended; entity has an ID but is no longer tracked by `EntityManager`.
4. **Removed**: Entity is scheduled for deletion when the transaction commits (`DELETE FROM...`).

---

## Key Annotations Breakdown

- `@Entity`: Marks a Java class as a JPA persistent domain entity.
- `@Table(name = "products")`: Maps entity class to explicit database table name.
- `@Id`: Specifies the Primary Key field.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Delegates Primary Key generation to database auto-increment columns.
- `@Column(name = "unit_price", precision = 10, scale = 2)`: Customizes database column properties (nullability, length, precision).
- `JpaRepository<T, ID>`: Spring Data abstraction extending `CrudRepository` and `PagingAndSortingRepository`, providing zero-boilerplate CRUD out of the box.

---

## Spring Data JPA Query Mechanics

### 1. Derived Query Methods
Spring Data JPA parses method signatures at startup using naming conventions:
```java
List<ProductEntity> findByCategoryAndPriceLessThan(String category, BigDecimal maxPrice);
```
Translates directly into SQL:
```sql
SELECT * FROM products WHERE category = ? AND unit_price < ?
```

### 2. Custom JPQL Queries
JPQL (Java Persistence Query Language) queries entity classes and fields instead of database tables and columns:
```java
@Query("SELECT p FROM ProductEntity p WHERE p.stockQuantity > :minStock ORDER BY p.price DESC")
List<ProductEntity> findAvailableHighValueProducts(@Param("minStock") Integer minStock);
```
Translates into database-agnostic SQL using Hibernate dialect (`H2Dialect`, `PostgreSQLDialect`, `MySQLDialect`).
