package com.platform.framework_vs_boot.component;

import com.platform.framework_vs_boot.repository.FrameworkRepository;
import com.platform.framework_vs_boot.service.FrameworkService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManualConfig {

    @Bean
    public FrameworkRepository manualRepository() {
        System.out.println("[ManualConfig] Manually instantiating and configuring FrameworkRepository.");
        return new FrameworkRepository("jdbc:postgresql://postgres.platform.internal:5432/core");
    }

    @Bean
    public FrameworkService manualService() {
        System.out.println("[ManualConfig] Manually instantiating FrameworkService and injecting manualRepository.");
        // Manual constructor injection wiring
        return new FrameworkService(manualRepository());
    }
}
