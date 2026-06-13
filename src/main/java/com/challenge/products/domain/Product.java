package com.challenge.products.domain;

import java.math.BigDecimal;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("products")
public record Product(
    @Id
    Long id,
    String name,
    String description,
    BigDecimal price,
    Integer stock,
    Instant createdAt,
    Instant updatedAt
) {
  public Product withId(Long newId) {
    return new Product(newId, name, description, price, stock, createdAt, updatedAt);
  }
}
