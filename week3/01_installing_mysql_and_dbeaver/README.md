# Topic 01: Installing MySQL & DBeaver GUI Database Environment Setup

## The First Principle: RDBMS Daemon Architecture, Storage Engines & Sockets

To understand relational persistence in Java, you must look below the framework layer at how a Relational Database Management System (RDBMS) operates at the Operating System and Network level:

1. **Database Daemon Process (`mysqld`)**: The MySQL database engine runs as an OS background service (`mysqld`). It manages system memory, process threads, file handles, and disk storage.
2. **InnoDB Storage Engine Architecture**:
   - **Buffer Pool**: A dedicated memory structure in RAM that caches table and index data. When Spring Boot executes a query, MySQL first checks if the data blocks are present in the Buffer Pool before performing costly disk I/O.
   - **Write-Ahead Logging (WAL / Redo Log)**: Before any data page is updated on disk, transaction changes are sequentially written to disk in the Redo Log (`ib_logfile`). This guarantees durability (ACID) even if the server crashes unexpectedly.
   - **B-Tree Indexes**: Primary key and secondary indexes are organized as B+Trees on disk, enabling logarithmic time complexity $O(\log N)$ for record lookups instead of full table scans $O(N)$.
3. **TCP Socket Listener**: The database daemon binds to a network TCP port (by default `3306`). It listens for incoming socket connections from database clients.
4. **JDBC Drivers**: A Java application cannot natively communicate with MySQL using plain Java methods. The **MySQL Connector/J** (`com.mysql.cj.jdbc.Driver`) is a socket-level driver that translates Java SQL commands into MySQL's binary Client/Server Protocol byte stream transmitted over TCP.

---

## Why-Not-Just-What: CLI vs Visual Database Administration (DBeaver)

### Raw MySQL CLI (`mysql -u root -p`)
```bash
mysql> SELECT * FROM users WHERE status = 'ACTIVE';
```
**Limitations of relying solely on command line tools during development:**
- **No Visual Schema Discovery**: Viewing table foreign key constraints, indexes, and column types requires running multiple verbose `DESCRIBE` and `SHOW CREATE TABLE` commands.
- **Complex Result Formatting**: Multi-column joins and JSON data types become unreadable text dumps in standard terminal screens.
- **Lack of Execution Plan Inspection**: Analyzing performance bottlenecks requires manually interpreting text-based `EXPLAIN` query outputs.

### DBeaver Universal Database Tool Solution
DBeaver is a free, cross-platform universal database GUI tool built on top of JDBC. It connects to any RDBMS via its JDBC driver.
- **Visual Entity-Relationship (ER) Diagrams**: Automatically generates interactive visual schema graphs from database foreign key metadata.
- **Live Connection Verification**: Allows immediate testing of database connectivity, user privileges, and socket availability independent of your Spring Boot code.
- **Data Editing & Raw SQL Execution**: Provides a rich SQL editor with autocompletion, parameter binding, and real-time execution statistics.

---

## Architecture Setup Pipeline

```text
===========================================================================================================
                                LOCAL DATABASE INTEGRATION ARCHITECTURE
===========================================================================================================

  [ SPRING BOOT APP ]                      [ DBEAVER GUI CLIENT ]
  (Spring Data JPA / HikariCP)             (Visual DB Manager)
          |                                        |
          | JDBC Connection Pool                   | JDBC Connection
          | (com.mysql.cj.jdbc.Driver)             | (com.mysql.cj.jdbc.Driver)
          | TCP Port 3306                          | TCP Port 3306
          v                                        v
+---------------------------------------------------------------------------------------------------------+
|                                    MYSQL DATABASE DAEMON (mysqld)                                      |
|                                                                                                         |
|  +---------------------------------------------------------------------------------------------------+  |
|  | NETWORK THREAD POOL (Listens on TCP 3306 for SQL Query Byte Streams)                              |  |
|  +---------------------------------------------------------------------------------------------------+  |
|                                                  |                                                      |
|  +-----------------------------------------------+---------------------------------------------------+  |
|  | INNODB STORAGE ENGINE                                                                             |  |
|  |                                                                                                   |  |
|  |   +---------------------------------------+       +-------------------------------------------+   |  |
|  |   | BUFFER POOL (RAM Cache)               |       | REDO LOG / WAL (Crash Recovery Disk)      |   |  |
|  |   +---------------------------------------+       +-------------------------------------------+   |  |
|  |                       |                                                 |                         |  |
|  |                       v                                                 v                         |  |
|  |   +-------------------------------------------------------------------------------------------+   |  |
|  |   | SYSTEM TABLES & DATA FILES (.ibd B-Tree Disk Storage)                                     |   |  |
|  |   +-------------------------------------------------------------------------------------------+   |  |
|  +---------------------------------------------------------------------------------------------------+  |
+---------------------------------------------------------------------------------------------------------+
===========================================================================================================
```

---

## Step-by-Step Setup Guide

### Option A: Running MySQL via Docker (Recommended)
Running MySQL inside a Docker container ensures an isolated, reproducible database instance without polluting your host operating system:

```bash
# Pull and start MySQL 8.0 container
docker run -d \
  --name mysql-masterclass \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=rootsecret \
  -e MYSQL_DATABASE=masterclass_db \
  mysql:8.0

# Verify container is running
docker ps
```

### Option B: Local Native MySQL Installation
1. Download MySQL Community Server (8.0+) from official MySQL downloads.
2. Complete setup wizard, set root password, and ensure service runs as background system service.

---

## User Privilege & Database Setup

Connect to MySQL as `root` and execute SQL script to create dedicated application user and database:

```sql
-- 1. Create target application database
CREATE DATABASE IF NOT EXISTS masterclass_db 
  CHARACTER SET utf8mb4 
  COLLATE utf8mb4_unicode_ci;

-- 2. Create application database user
CREATE USER IF NOT EXISTS 'devuser'@'%' IDENTIFIED BY 'devpassword';

-- 3. Grant full DDL and DML privileges on masterclass_db
GRANT ALL PRIVILEGES ON masterclass_db.* TO 'devuser'@'%';

-- 4. Apply privilege changes
FLUSH PRIVILEGES;
```

---

## Configuring DBeaver GUI Connection

1. Open **DBeaver** -> Select **Database** -> **New Database Connection**.
2. Choose **MySQL** from driver list.
3. Configure Main connection properties:
   - **Host**: `localhost`
   - **Port**: `3306`
   - **Database**: `masterclass_db`
   - **Username**: `devuser`
   - **Password**: `devpassword`
4. Click **Test Connection...** (DBeaver will automatically download `mysql-connector-j` driver if missing).
5. Upon successful connection notice, click **Finish**.

---

## Spring Boot Connection Configuration

Add the following settings to `src/main/resources/application.properties` in your Spring Boot application:

```properties
# DataSource Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/masterclass_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=devuser
spring.datasource.password=devpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Connection Pool Settings (HikariCP)
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.connection-timeout=20000

# JPA / Hibernate SQL Properties
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## Common Connection Pitfalls & Diagnostics

1. **`Public Key Retrieval is not allowed`**:
   - *Cause*: MySQL 8.0 uses `caching_sha2_password` authentication by default, requiring RSA public key exchange over unencrypted connections.
   - *Fix*: Append `allowPublicKeyRetrieval=true&useSSL=false` to JDBC connection URL.
2. **`Communications link failure`**:
   - *Cause*: `mysqld` process is stopped or port `3306` is blocked by firewall/Docker mapping.
   - *Fix*: Verify MySQL daemon status via `docker ps` or OS services panel.
3. **`Access denied for user 'devuser'@'localhost'`**:
   - *Cause*: Host restriction mismatch in MySQL user privileges (`'devuser'@'%'` vs `'devuser'@'localhost'`).
   - *Fix*: Ensure privileges are granted with `%` host wildcard or execute `FLUSH PRIVILEGES`.
