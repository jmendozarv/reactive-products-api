package com.challenge.products.service;

import com.challenge.products.domain.Product;
import com.challenge.products.dto.ProductRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
  Mono<Product> create(ProductRequest request);
  Flux<Product> findAll();
  Mono<Product> findById(Long id);
  Mono<Product> update(Long id, ProductRequest request);
  Mono<Void> delete(Long id);
}
