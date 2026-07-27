package com.platform.presentation.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
    Long id,
    String name,
    String category,
    BigDecimal price,
    LocalDateTime createdAt
) {}
