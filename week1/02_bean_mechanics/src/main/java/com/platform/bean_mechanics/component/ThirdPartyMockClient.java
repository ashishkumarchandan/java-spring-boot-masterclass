package com.platform.bean_mechanics.component;

// Simulated external library class (no @Component annotation)
public class ThirdPartyMockClient {
    private final String endpoint;

    public ThirdPartyMockClient(String endpoint) {
        this.endpoint = endpoint;
        System.out.println("[ThirdPartyMockClient] Initialized with endpoint: " + endpoint);
    }

    public String fetchApiResponse() {
        return "Response from external API: " + endpoint;
    }
}
