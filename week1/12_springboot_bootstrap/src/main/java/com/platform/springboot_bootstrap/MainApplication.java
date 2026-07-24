package com.platform.springboot_bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        System.out.println("[Main] STARTING SPRINGAPPLICATION BOOTSTRAP TRACER");
        SpringApplication app = new SpringApplication(MainApplication.class);

        // Disable default spring banner for cleaner log validation
        app.setBannerMode(org.springframework.boot.Banner.Mode.OFF);

        // Register custom startup event listeners to trace the 7 internal bootstrap stages
        app.addListeners(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                if (event instanceof ApplicationStartingEvent) {
                    System.out.println("[Bootstrap Stage 1] Starting: SpringApplication run() initiated. Listeners started.");
                } else if (event instanceof ApplicationEnvironmentPreparedEvent) {
                    System.out.println("[Bootstrap Stage 2] Environment Prepared: Classpath and profiles loaded.");
                } else if (event instanceof ApplicationContextInitializedEvent) {
                    System.out.println("[Bootstrap Stage 3] Context Initialized: Empty ApplicationContext created.");
                } else if (event instanceof ApplicationPreparedEvent) {
                    System.out.println("[Bootstrap Stage 4] Context Prepared: Primary source bean definitions loaded.");
                } else if (event instanceof ContextRefreshedEvent) {
                    System.out.println("[Bootstrap Stage 5] Context Refreshed: Singleton instances constructed.");
                } else if (event instanceof ApplicationStartedEvent) {
                    System.out.println("[Bootstrap Stage 6] Application Started: Embedded server online, runners searching.");
                } else if (event instanceof ApplicationReadyEvent) {
                    System.out.println("[Bootstrap Stage 7] Ready: Bootstrapping complete. Application serving requests.");
                }
            }
        });

        app.run(args);
    }
}
