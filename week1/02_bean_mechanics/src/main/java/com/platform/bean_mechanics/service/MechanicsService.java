package com.platform.bean_mechanics.service;

import com.platform.bean_mechanics.component.ThirdPartyMockClient;
import com.platform.bean_mechanics.repository.MechanicsRepository;
import org.springframework.stereotype.Component;

@Component
public class MechanicsService {

    private final MechanicsRepository repository;
    private final ThirdPartyMockClient mockClient;

    public MechanicsService(MechanicsRepository repository, ThirdPartyMockClient mockClient) {
        this.repository = repository;
        this.mockClient = mockClient;
        System.out.println("[MechanicsService] Instantiated using @Component scanning.");
    }

    public String assemblePayload() {
        return "Service processing [" + repository.loadLocalData() + "] and external [" + mockClient.fetchApiResponse() + "]";
    }
}
