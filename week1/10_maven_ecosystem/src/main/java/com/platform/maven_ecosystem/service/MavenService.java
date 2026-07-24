package com.platform.maven_ecosystem.service;

import com.platform.maven_ecosystem.repository.MavenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MavenService {
    private final MavenRepository repository;

    @Autowired
    public MavenService(MavenRepository repository) {
        this.repository = repository;
    }

    public String runBuildAudit() {
        return "Build audit success for: " + repository.getArtifactInfo();
    }
}
