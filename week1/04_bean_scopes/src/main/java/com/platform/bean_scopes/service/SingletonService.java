package com.platform.bean_scopes.service;

import com.platform.bean_scopes.component.PrototypeProcessor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingletonService {

    // 1. Direct Field Injection (Trap: This instance will be frozen and reused)
    @Autowired
    private PrototypeProcessor frozenProcessor;

    // 2. ObjectFactory Injection (Solution: Resolves a fresh instance on demand)
    @Autowired
    private ObjectFactory<PrototypeProcessor> processorFactory;

    public SingletonService() {
        System.out.println("[SingletonService] Instantiated as Singleton.");
    }

    public String runWithTrap(String data) {
        return frozenProcessor.processRequest(data);
    }

    public String runWithSolution(String data) {         
        // Retrieve a brand new instance from the container registry dynamically
        PrototypeProcessor freshProcessor = processorFactory.getObject();
        return freshProcessor.processRequest(data);
    }

    public int getFrozenProcessorHash() {
        return System.identityHashCode(frozenProcessor);
    }
}
