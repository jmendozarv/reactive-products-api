package com.challenge.products.dto;

import com.challenge.products.domain.Product;
import java.math.BigDecimal;
import java.time.Instant;

public record ProductResponse(
    Long id,
    String name,
    String description,
    BigDecimal price,
    Integer stock,
    Instant createdAt,
    Instant updatedAt
) {
  public static ProductResponse from(Product product) {
    return new ProductResponse(
        product.id(),
        product.name(),
        product.description(),
        product.price(),
        product.stock(),
        product.createdAt(),
        product.updatedAt()
    );
  }
}
