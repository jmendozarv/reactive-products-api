package com.challenge.products.service.impl;

import com.challenge.products.domain.Product;
import com.challenge.products.dto.ProductRequest;
import com.challenge.products.exception.InvalidProductException;
import com.challenge.products.repository.ProductRepository;
import com.challenge.products.service.ProductService;
import java.time.Clock;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private final ProductRepository repository;

  ProductServiceImpl(ProductRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<Product> create(ProductRequest request) {
    return Mono.just(request)
        .map(this::toNewProduct)
        .flatMap(repository::save);
  }

  @Override
  public Flux<Product> findAll() {
    return null;
  }

  @Override
  public Mono<Product> findById(Long id) {
    return null;
  }

  @Override
  public Mono<Product> update(Long id, ProductRequest request) {
    return null;
  }

  @Override
  public Mono<Void> delete(Long id) {
    return null;
  }


  private Product toNewProduct(ProductRequest request) {
    Instant now = Clock.systemUTC().instant();
    return new Product(
        null,
        normalizedName(request),
        normalizedDescription(request),
        request.price(),
        request.stock(),
        now,
        now
    );
  }

  private String normalizedName(ProductRequest request) {
    return Optional.ofNullable(request.name())
        .map(String::trim)
        .filter(name -> !name.isBlank())
        .orElseThrow(() -> new InvalidProductException("name is required"));
  }

  private String normalizedDescription(ProductRequest request) {
    return Optional.ofNullable(request.description())
        .map(String::trim)
        .filter(description -> !description.isBlank())
        .orElse(null);
  }

}
