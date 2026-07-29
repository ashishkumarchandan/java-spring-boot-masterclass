# Week 3: Database Persistence, Hibernate, and Spring Data JPA

Welcome to **Week 3** of the Java & Spring Boot Masterclass! This week builds a deep, first-principles understanding of enterprise persistence, Object-Relational Mapping (ORM), Spring Data JPA, database transactions, cloud PostgreSQL integration, and advanced query strategies.

---

## 📂 Module Reference Guide

Week 3 is structured into **9 self-contained modules**. Each module provides an in-depth `README.md` detailing hardware/database engine internals, architecture diagrams, core annotations, code & configuration snippets, and enterprise best practices.

### [01. Installing MySQL & DBeaver](./01_installing_mysql_and_dbeaver) (`01_installing_mysql_and_dbeaver`)
- **Focus**: Setting up local database infrastructure and administration tools.
- **Key Concepts**: RDBMS daemon (`mysqld`), InnoDB storage engine architecture, TCP/IP socket listening (Port 3306), DBeaver visual administration tool, user privileges (`GRANT ALL PRIVILEGES`), HikariCP connection pooling, JDBC connection URLs (`allowPublicKeyRetrieval=true`).
- **Setup Demo**: Docker MySQL container setup, DBeaver connection configuration, and Spring Boot datasource setup.

### [02. Hibernate & JPA Fundamentals](./02_hibernate_and_jpa) (`02_hibernate_and_jpa`)
- **Focus**: Mastering Object-Relational Mapping (ORM).
- **Key Concepts**: Object-Relational Impedance Mismatch, JPA specification (`jakarta.persistence.*`) vs. Hibernate engine (`org.hibernate.*`), configuration properties (`ddl-auto`, `show-sql`, `format_sql`, `database-platform`), entity mapping annotations (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`, `@Enumerated`).
- **Code Demo**: Complete `ProductEntity` mapping Java attributes to database columns and auto DDL execution.

### [03. Spring Data JPA & Dynamic Queries](./03_spring_data_jpa_and_dynamic_queries) (`03_spring_data_jpa_and_dynamic_queries`)
- **Focus**: Eliminating DAO boilerplate code with Spring Data repositories.
- **Key Concepts**: JDK Dynamic Proxies (`SimpleJpaRepository`), repository pattern, derived query method parsing algorithms (`findBy`, `Distinct`, `Top`, `And`, `Or`, `LessThan`, `GreaterThan`, `Containing`, `IgnoreCase`).
- **Code Demo**: `ProductRepository` interface executing type-safe queries without concrete DAO implementation classes.

### [04. Sorting & Pagination](./04_sorting_and_pagination) (`04_sorting_and_pagination`)
- **Focus**: Handling large datasets efficiently without memory exhaustion.
- **Key Concepts**: In-memory sorting vs SQL `LIMIT` / `OFFSET` index scanning, `Sort` class (static & dynamic sorting, null handling), `Pageable`, `PageRequest`, `Page<T>` (with total count query) vs. `Slice<T>` (for high-performance infinite scrolling).
- **Code Demo**: REST controller binding `@PageableDefault` query parameters into paginated API responses.

### [05. Entity Lifecycle & Relationships](./05_entity_lifecycle_and_relationships) (`05_entity_lifecycle_and_relationships`)
- **Focus**: Deep dive into JPA mechanics and multi-entity associations.
- **Key Concepts**: `PersistenceContext` (L1 Cache), automatic Dirty Checking, 4 JPA Entity States (Transient, Managed, Detached, Removed), `@OneToMany` & `@ManyToOne` mapping, owning side vs. inverse side (`mappedBy`), Cascade Types (`ALL`, `PERSIST`, `REMOVE`), `orphanRemoval = true`, `LAZY` vs `EAGER` fetching, N+1 SELECT query problem.
- **Code Demo**: Bidirectional `Department` <---> `Employee` domain model with synchronized collection helper methods.

### [06. Homework Projects](./06_homework) (`06_homework`)
- **Focus**: Real-world hands-on domain modeling and CRUD REST APIs.
- **Key Concepts**: Relational domain modeling, ER diagrams (Mermaid), REST API matrices, multi-entity relationships.
- **Projects**:
  1. **Library Management System**: `Author` (1) <---> (N) `Book` with availability status tracking.
  2. **College Management System**: `Professor`, `Student`, `Subject`, `AdmissionRecord` with 1:1, 1:N, and N:M associations.

### [07. PostgreSQL Cloud Integration](./07_postgresql_cloud_integration) (`07_postgresql_cloud_integration`)
- **Focus**: Deploying Spring Boot database applications to cloud environments.
- **Key Concepts**: Encrypted TCP/IP sockets over TLS/SSL (`sslmode=require`), `org.postgresql.Driver`, `PostgreSQLDialect`, HikariCP cloud connection pool tuning (keep-alive probes, max-lifetime), externalizing secrets via environment variables (`SPRING_DATASOURCE_URL`).
- **Setup Demo**: Connecting Spring Boot to cloud-hosted PostgreSQL instances (Neon, Render, Supabase).

### [08. JPQL & Native Queries](./08_jpql_and_native_queries) (`08_jpql_and_native_queries`)
- **Focus**: Writing custom, complex database queries beyond derived method names.
- **Key Concepts**: JPQL (Java Persistence Query Language) targeting entity graphs vs. Native SQL targeting database tables, named parameter binding (`@Param`), DTO constructor projections (`SELECT new Dto(...)`), bulk update/delete operations (`@Modifying`).
- **Code Demo**: Custom `@Query` interface methods returning projected DTO payloads and executing bulk entity modifications.

### [09. Database Transactions](./09_database_transactions) (`09_database_transactions`)
- **Focus**: Guaranteeing data safety, consistency, and transaction boundaries.
- **Key Concepts**: ACID Guarantees (Atomicity, Consistency, Isolation, Durability), `ThreadLocal` connection binding (`TransactionSynchronizationManager`), Spring AOP Transactional Proxies, rollback rules (`rollbackFor = Exception.class`), propagation types (`REQUIRED`, `REQUIRES_NEW`), isolation levels, `@Transactional(readOnly = true)` performance hints.
- **Code Demo**: `StudentEnrollmentService` managing multi-repository "All-or-Nothing" atomic operations.

---

## 🛠️ How to Compile & Run

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- MySQL 8.0+ / PostgreSQL or Docker installed
- DBeaver GUI (optional, recommended)

### Running via an IDE (Recommended)
1. Import the root repository in **IntelliJ IDEA**, **Eclipse**, or **VS Code**.
2. Open any sub-module's `README.md` to explore concepts, entity mappings, and architecture diagrams.
3. Configure your local `application.properties` with your database credentials.
