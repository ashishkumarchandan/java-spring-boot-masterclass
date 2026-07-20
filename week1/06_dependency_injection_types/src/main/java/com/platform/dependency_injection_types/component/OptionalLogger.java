package com.platform.dependency_injection_types.component;

import org.springframework.stereotype.Component;

@Component
public class OptionalLogger {
    public void logTrace(String msg) {
        System.out.println("[OptionalLogger - TRACE] " + msg);
    }
}
