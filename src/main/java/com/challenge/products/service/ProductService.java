package com.challenge.products.service;

import com.challenge.products.dto.ProductRequest;
import com.challenge.products.dto.ProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
  Mono<ProductResponse> createProduct(ProductRequest request);
  Flux<ProductResponse> getAllProducts();
  Mono<ProductResponse> getProductById(Long id);
  Mono<ProductResponse> updateProduct(Long id, ProductRequest request);
  Mono<Void> deleteProduct(Long id);
}
