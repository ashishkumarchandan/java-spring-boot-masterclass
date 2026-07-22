package com.platform.bean_mechanics.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ThirdPartyMockClient thirdPartyMockClient() {
        System.out.println("[AppConfig] Executing @Bean factory method for ThirdPartyMockClient.");
        return new ThirdPartyMockClient("https://api.external.com/v1");
    }
}
