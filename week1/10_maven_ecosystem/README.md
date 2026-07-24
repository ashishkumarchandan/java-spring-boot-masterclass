# Topic 10: Maven Build Ecosystem & Lifecycle Phases

## The First Principle: Standardized Classpaths and Transitive Resolution

In Java, code compilation and execution rely on the Classpath—a list of paths where the JVM searches for `.class` files. Historically, managing libraries meant manually downloading `.jar` files and placing them in a `lib/` folder. This approach causes several problems:
1.  **Dependency Hell**: If library `A` requires library `B` (version 1.0) and library `C` requires library `B` (version 2.0), manually placing both in the folder causes classloading conflicts. The JVM loads whichever jar it scans first, resulting in intermittent `NoSuchMethodError` or `NoClassDefFoundError` at runtime.
2.  **Lack of Lifecycle Structure**: There was no standard way to compile, run tests, and package archives, forcing developers to write custom shell scripts or complex Ant builds that vary between projects.

**Apache Maven** solves this by providing:
*   **Declarative Dependency Management**: You declare the immediate libraries your code needs. Maven parses their metadata, builds a Dependency Tree, and transitively fetches all sub-dependencies.
*   **Nearest Definition Conflict Resolution**: If conflicts arise (e.g., different versions of the same jar), Maven selects the version closest to the root of the dependency tree. If the distance is equal, it selects the first declared dependency.
*   **Standardized Build Lifecycles**: A build is divided into standard phases that execute sequentially.

---

## The Build Lifecycles & Dependency Scopes

### 1. The Default Lifecycle:
Maven has three built-in lifecycles: `default` (handles deployment), `clean` (handles project cleaning), and `site` (handles project site creation).
The **Default Lifecycle** comprises linear phases. Executing a phase runs all preceding phases automatically:

```text
validate -> compile -> test-compile -> test -> package -> verify -> install -> deploy
```

*   **validate**: Confirms the project is correct and all necessary information is available.
*   **compile**: Compiles the source code of the project.
*   **test**: Runs tests using a suitable testing framework (e.g., JUnit). These tests should not require the package to be deployed.
*   **package**: Packages the compiled code in its distributable format, such as a JAR or WAR.
*   **install**: Installs the package into the local repository (`~/.m2/repository`) for use as a dependency in other local projects.

### 2. Dependency Scopes:
Maven dynamically modifies the Classpath passed to the compiler and JVM depending on the active build phase:

| Scope | Compiled Classpath | Tested Classpath | Runtime Classpath | Packaged in Archive? | Example Use Case |
| :--- | :---: | :---: | :---: | :---: | :--- |
| **`compile`** (Default) | Yes | Yes | Yes | Yes | Logback, Spring Core |
| **`provided`** | Yes | Yes | No | No | Servlet API, Lombok |
| **`runtime`** | No | Yes | Yes | Yes | JDBC Driver Implementation |
| **`test`** | No | Yes | No | No | JUnit, Mockito |

---

## Classpath Construction and Lifecycle Phases

```text
==========================================================================================
                      MAVEN CLASSPATH SCOPE EXCLUSION
==========================================================================================

                          [ Compile Phase Classpath ]
                          +-------------------------+
                          | - Source Code (.java)   |
                          | - Compile Scope Jars    |
                          | - Provided Scope Jars   |  <-- Needed to compile code
                          +-------------------------+
                                       |
                                       v
                          [ Test Phase Classpath ]
                          +-------------------------+
                          | - Compiled Code (.class)|
                          | - Compile Scope Jars    |
                          | - Provided Scope Jars   |
                          | - Test Scope Jars       |  <-- JUnit, Mockito active
                          +-------------------------+
                                       |
                                       v
                          [ Runtime / Package Phase Classpath ]
                          +----------------------------------+
                          | - Compiled Code (.class)         |
                          | - Compile Scope Jars             |
                          | - Runtime Scope Jars             |
                          | (Provided & Test scopes EXCLUDED)| <-- Packaged into Fat JAR
                          +----------------------------------+
==========================================================================================
```
