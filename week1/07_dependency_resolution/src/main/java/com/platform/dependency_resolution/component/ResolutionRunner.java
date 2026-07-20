package com.platform.dependency_resolution.component;

import com.platform.dependency_resolution.repository.SmsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ResolutionRunner implements CommandLineRunner {

    private final SmsProvider primaryProvider;
    private final SmsProvider qualifiedProvider;

    @Autowired
    public ResolutionRunner(
            SmsProvider primaryProvider,
            @Qualifier("plivoProvider") SmsProvider qualifiedProvider) {
        this.primaryProvider = primaryProvider;
        this.qualifiedProvider = qualifiedProvider;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================================");
        System.out.println("[ResolutionRunner] Running Ambiguity Resolution Demo:");
        System.out.println("  -> Primary provider injected: " + primaryProvider.getClass().getSimpleName() 
                + " (Action: " + primaryProvider.sendSms("Hello") + ")");
        System.out.println("  -> Qualified provider injected (@Qualifier(\"plivoProvider\")): " 
                + qualifiedProvider.getClass().getSimpleName() 
                + " (Action: " + qualifiedProvider.sendSms("Hello") + ")");
        System.out.println("========================================================\n");
    }
}
