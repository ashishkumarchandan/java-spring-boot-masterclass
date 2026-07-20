CRITICAL WORKSPACE-WIDE REFACTOR: ALTERATION OF ALL 12 TOPICS TO PRODUCTION STRUCTURE

We are completely eliminating flat files and sandboxes from this entire project. Every single topic folder from 01 to 12 must strictly use the standard Maven enterprise project structure.

For EACH folder from `01_core_container/` to `12_springboot_bootstrap/`, execute the following structural refactor:

1. DELETE any flat 'ExampleOne.java' or 'ExampleTwo.java' files floating in the root of the topic folder.
2. CREATE the standard production directory tree inside that topic folder:
   ├── src/
   │   └── main/
   │       ├── java/
   │       │   └── com/
   │       │       └── platform/
   │       │           └── [topic_name]/
   │       │               ├── MainApplication.java (Annotated with @SpringBootApplication)
   │       │               ├── controller/
   │       │               ├── service/
   │       │               ├── repository/
   │       │               └── component/
   │       └── resources/
   │           └── application.properties

3. CONTEXT-SPECIFIC LAYER REQUIREMENT FOR EACH TOPIC:
   Inside the java packages for each topic, implement functional, compiling code where:
   - The Repository handles data simulation.
   - The Service handles the business logic processing.
   - The Controller exposes a REST API endpoint demonstrating the theme.
   - A component implementing CommandLineRunner runs on startup to print out terminal logs demonstrating the exact mechanics of that topic.

SPECIFIC CONTENT MATRIX TO IMPLEMENT ACROSS THE FOLDERS:
- 01_core_container: Wire components across layers to show how the IoC container holds references.
- 02_bean_mechanics: Use @Component for the service layer and a @Configuration/@Bean class to instantiate an external library simulation.
- 03_bean_lifecycle: Place @PostConstruct and @PreDestroy hooks inside the service layer to log the container startup/shutdown sequence.
- 04_bean_scopes: Inject a Prototype-scoped request processor into a Singleton-scoped service to demonstrate the mixed-scope trap.
- 05_coupling_and_ioc: Show a decoupled architecture where the service layer depends entirely on an interface implemented by different repository variants.
- 06_dependency_injection_types: Implement Constructor injection in the service layer, Setter injection for optional components, and explain why Field injection is missing.
- 07_dependency_resolution: Create two repository beans of the same interface and resolve the ambiguity using @Qualifier in one controller and @Primary in another.
- 08_advanced_injection: Inject a List or Map of all matching strategy beans into a service component to dynamically resolve tasks.
- 09_framework_vs_boot: Set up an explicit configuration bean that shows how much manual wiring Spring Framework used to require vs Boot's automation.
- 10_maven_ecosystem: Verify the main pom.xml is correctly coordinating dependencies for all sub-modules.
- 11_autoconfigure_conditional: Use @ConditionalOnProperty or @ConditionalOnClass to conditionally activate a mock payment-gateway component.
- 12_springboot_bootstrap: Map out the 7 steps of the internal bootstrap sequence using deep log statements inside the main application startup flow.

Ensure every single file has a clear 'package com.platform.[topic_name]...' header, valid imports, and compiles perfectly. Run this refactor completely across all 12 directories now.





last prompt was faulty C  this si new one but i say be dynamic the guy who gave me last one gave me this one now use ur magic enhace this too u are much more knowlegable than me i need to learn topics teach me best way