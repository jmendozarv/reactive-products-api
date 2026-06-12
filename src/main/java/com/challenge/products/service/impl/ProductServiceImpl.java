package com.challenge.products.service.impl;

import com.challenge.products.dto.ProductRequest;
import com.challenge.products.dto.ProductResponse;
import com.challenge.products.service.ProductService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

  @Override
  public Mono<ProductResponse> createProduct(ProductRequest request) {
    return null;
  }

  @Override
  public Flux<ProductResponse> getAllProducts() {
    return null;
  }

  @Override
  public Mono<ProductResponse> getProductById(Long id) {
    return null;
  }

  @Override
  public Mono<ProductResponse> updateProduct(Long id, ProductRequest request) {
    return null;
  }

  @Override
  public Mono<Void> deleteProduct(Long id) {
    return null;
  }
}
