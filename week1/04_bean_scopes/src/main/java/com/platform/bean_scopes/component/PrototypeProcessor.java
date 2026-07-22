package com.platform.bean_scopes.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PrototypeProcessor {
    private final int instanceId;
    private static int globalCounter = 0;

    public PrototypeProcessor() {
        synchronized (PrototypeProcessor.class) {
            globalCounter++;
            this.instanceId = globalCounter;
        }
        System.out.println("[PrototypeProcessor] New Instance constructed. ID: " + instanceId 
                + " | Hash: " + System.identityHashCode(this));
    }

    public String processRequest(String data) {
        return "Processed by prototype ID: " + instanceId + " (hash: " + System.identityHashCode(this) + ") with data: " + data;
    }
}
