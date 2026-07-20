package com.platform.dependency_injection_types.service;

import com.platform.dependency_injection_types.component.OptionalLogger;
import com.platform.dependency_injection_types.repository.DiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiService {

    // 1. Mandatory dependency: constructor injection guarantees immutability
    private final DiRepository repository;

    // 2. Optional dependency: setter injection allows mutability / nullable checks
    private OptionalLogger optionalLogger;

    /**
     * CONSTRUCTOR DI (Best Practice):
     * - Allows declaring the field as final, guaranteeing thread-safety.
     * - Enforces the compiler to verify class initialization, preventing NullPointerExceptions.
     * - Simplifies unit tests (no Spring context or reflection needed to pass a mock repository).
     */
    @Autowired
    public DiService(DiRepository repository) {
        this.repository = repository;
        System.out.println("[DiService] MANDATORY Constructor DI completed successfully.");
    }

    /**
     * SETTER DI:
     * - Ideal for optional dependencies or configurations that can change at runtime.
     * - Does not enforce instantiation constraints during compilation.
     */
    @Autowired(required = false)
    public void setOptionalLogger(OptionalLogger optionalLogger) {
        this.optionalLogger = optionalLogger;
        System.out.println("[DiService] OPTIONAL Setter DI completed. Logger set.");
    }

    /**
     * WHY FIELD INJECTION IS MISSING:
     * - It hides dependencies from public constructors, making APIs harder to discover.
     * - It makes testing without container injection tools difficult, as you cannot assign mock fields.
     * - It prevents dependencies from being final.
     */

    public String fetchResource() {
        if (optionalLogger != null) {
            optionalLogger.logTrace("Processing request to fetch resources.");
        }
        return repository.fetchSecurePayload();
    }

    public boolean hasLogger() {
        return optionalLogger != null;
    }
}
