package com.platform.maven_ecosystem.repository;

import org.springframework.stereotype.Repository;

@Repository
public class MavenRepository {
    public String getArtifactInfo() {
        return "Artifact: 10_maven_ecosystem | Version: 1.0-SNAPSHOT";
    }
}
