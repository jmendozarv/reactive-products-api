package com.challenge.products.domain;

import org.springframework.data.relational.core.mapping.Table;

@Table("products")
public record Product(
    Long id,
    String name,
    String description,
    Double price,
    Integer stock,
    String createdAt,
    String updatedAt
) {
  public Product withId(Long newId) {
    return new Product(newId, name, description, price, stock, createdAt, updatedAt);
  }
}
