package com.challenge.products.repository;

import com.challenge.products.domain.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
}
