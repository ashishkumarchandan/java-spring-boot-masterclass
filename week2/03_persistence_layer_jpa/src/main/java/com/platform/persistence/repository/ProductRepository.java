package com.platform.persistence.repository;

import com.platform.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    // Derived Query Method (Spring Data JPA auto-generates SQL query)
    List<ProductEntity> findByCategory(String category);

    // Derived Query with comparison
    List<ProductEntity> findByCategoryAndPriceLessThan(String category, BigDecimal maxPrice);

    // Custom JPQL Query (Object-Oriented SQL over Entities)
    @Query("SELECT p FROM ProductEntity p WHERE p.stockQuantity > :minStock ORDER BY p.price DESC")
    List<ProductEntity> findAvailableHighValueProducts(@Param("minStock") Integer minStock);
}
