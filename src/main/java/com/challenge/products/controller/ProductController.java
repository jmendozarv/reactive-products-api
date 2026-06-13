package com.challenge.products.controller;

import com.challenge.products.dto.ProductRequest;
import com.challenge.products.dto.ProductResponse;
import com.challenge.products.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
    return service.create(request)
        .map(ProductResponse::from);
  }

  @GetMapping
  public Flux<ProductResponse> findAll() {
    return service.findAll()
        .map(ProductResponse::from);
  }

  @GetMapping("/{id}")
  public Mono<ProductResponse> findById(@PathVariable Long id) {
    return service.findById(id)
        .map(ProductResponse::from);
  }


}
