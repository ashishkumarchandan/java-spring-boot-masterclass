package com.platform.persistence;

import com.platform.persistence.entity.ProductEntity;
import com.platform.persistence.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class PersistenceJpaApplication {

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("🗄️ Bootstrapping JPA & Hibernate Persistence Layer...");
        System.out.println("=================================================================");
        SpringApplication.run(PersistenceJpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner jpaDemoRunner(ProductRepository productRepository) {
        return args -> {
            System.out.println("\n-----------------------------------------------------------------");
            System.out.println("1️⃣ STEP 1: Creating & Persisting Entities using Lombok @Builder");
            System.out.println("-----------------------------------------------------------------");
            
            // Using Lombok's @Builder pattern for clean, readable instantiation
            ProductEntity laptop = ProductEntity.builder()
                    .name("MacBook Pro 16")
                    .category("Electronics")
                    .price(new BigDecimal("2499.00"))
                    .stockQuantity(15)
                    .build();

            ProductEntity monitor = ProductEntity.builder()
                    .name("4K Gaming Monitor")
                    .category("Electronics")
                    .price(new BigDecimal("499.99"))
                    .stockQuantity(30)
                    .build();

            ProductEntity desk = ProductEntity.builder()
                    .name("Ergonomic Standing Desk")
                    .category("Furniture")
                    .price(new BigDecimal("650.00"))
                    .stockQuantity(5)
                    .build();

            // Save triggers Hibernate INSERT statements -> Managed State
            productRepository.saveAll(List.of(laptop, monitor, desk));
            System.out.println("✅ Inserted 3 products into H2 database via Lombok @Builder.");
            System.out.println("   📦 [Lombok @ToString output]: " + laptop);

            System.out.println("\n-----------------------------------------------------------------");
            System.out.println("2️⃣ STEP 2: Executing Derived Query Method findByCategory('Electronics')");
            System.out.println("-----------------------------------------------------------------");
            List<ProductEntity> electronics = productRepository.findByCategory("Electronics");
            electronics.forEach(p -> System.out.println("   📦 [Found]: " + p.getName() + " | Price: $" + p.getPrice()));

            System.out.println("\n-----------------------------------------------------------------");
            System.out.println("3️⃣ STEP 3: Executing Custom JPQL Query findAvailableHighValueProducts(10)");
            System.out.println("-----------------------------------------------------------------");
            List<ProductEntity> highValue = productRepository.findAvailableHighValueProducts(10);
            highValue.forEach(p -> System.out.println("   💎 [High Value]: " + p.getName() + " | Stock: " + p.getStockQuantity()));
            System.out.println("=================================================================\n");
        };
    }
}
