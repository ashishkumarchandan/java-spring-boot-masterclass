package com.platform.presentation.controller;

import com.platform.presentation.model.ProductRequest;
import com.platform.presentation.model.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1/products")
public class ProductApiController {

    private final Map<Long, ProductResponse> productStore = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(100);

    public ProductApiController() {
        // Seed in-memory data
        productStore.put(101L, new ProductResponse(101L, "Spring Boot Microservices Guide", "Books", new BigDecimal("49.99"), LocalDateTime.now()));
        productStore.put(102L, new ProductResponse(102L, "Mechanical Keyboard", "Electronics", new BigDecimal("129.50"), LocalDateTime.now()));
    }

    // GET /api/v1/products?category=Electronics
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestHeader(name = "User-Agent", required = false) String userAgent) {
        
        System.out.println("📥 GET /api/v1/products called. Filter category: " + category + " | Client User-Agent: " + userAgent);
        
        List<ProductResponse> results = productStore.values().stream()
                .filter(p -> category == null || p.category().equalsIgnoreCase(category))
                .toList();

        return ResponseEntity.ok(results);
    }

    // GET /api/v1/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        System.out.println("📥 GET /api/v1/products/" + id);
        ProductResponse product = productStore.get(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    // POST /api/v1/products
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        System.out.println("📥 POST /api/v1/products with body: " + request.getName());
        Long newId = idSequence.incrementAndGet();
        ProductResponse created = new ProductResponse(
                newId,
                request.getName(),
                request.getCategory(),
                request.getPrice(),
                LocalDateTime.now()
        );
        productStore.put(newId, created);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/v1/products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
        System.out.println("📥 PUT /api/v1/products/" + id);
        if (!productStore.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        ProductResponse updated = new ProductResponse(
                id,
                request.getName(),
                request.getCategory(),
                request.getPrice(),
                LocalDateTime.now()
        );
        productStore.put(id, updated);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/v1/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        System.out.println("📥 DELETE /api/v1/products/" + id);
        if (productStore.remove(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
