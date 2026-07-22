package com.platform.core_container.service;

import com.platform.core_container.repository.CoreRepository;
import org.springframework.stereotype.Service;

@Service
public class CoreService {
    private final CoreRepository repository;

   
    public CoreService(CoreRepository repository) {
        this.repository = repository;
        System.out.println("[CoreService] Instantiated by Spring Container. Autowired CoreRepository Hash: " 
                + System.identityHashCode(repository));
    }

    public String processData() {
        return "CoreService -> " + repository.getData();
    }
}
