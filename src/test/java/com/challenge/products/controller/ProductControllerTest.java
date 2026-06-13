package com.challenge.products.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.challenge.products.domain.Product;
import com.challenge.products.dto.ProductRequest;
import com.challenge.products.exception.ProductNotFoundException;
import com.challenge.products.service.ProductService;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(ProductController.class)
class ProductControllerTest {

  private static final Instant NOW = Instant.parse("2026-06-12T20:00:00Z");

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private ProductService service;

  @Test
  void listsProducts() {
    when(service.findAll()).thenReturn(Flux.just(product(1L, "Laptop")));

    webTestClient.get()
        .uri("/api/v1/products")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$[0].id").isEqualTo(1)
        .jsonPath("$[0].name").isEqualTo("Laptop");
  }

  @Test
  void getsProductById() {
    when(service.findById(1L)).thenReturn(Mono.just(product(1L, "Laptop")));

    webTestClient.get()
        .uri("/api/v1/products/1")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.id").isEqualTo(1)
        .jsonPath("$.name").isEqualTo("Laptop");
  }

  @Test
  void returnsNotFoundWhenProductDoesNotExist() {
    when(service.findById(99L)).thenReturn(Mono.error(new ProductNotFoundException(99L)));

    webTestClient.get()
        .uri("/api/v1/products/99")
        .exchange()
        .expectStatus().isNotFound()
        .expectBody()
        .jsonPath("$.status").isEqualTo(404)
        .jsonPath("$.message").isEqualTo("Product with id 99 was not found");
  }

  @Test
  void createsProduct() {
    when(service.create(any(ProductRequest.class))).thenReturn(Mono.just(product(5L, "Mouse")));

    webTestClient.post()
        .uri("/api/v1/products")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("""
            {
              "name": "Mouse",
              "description": "Wireless",
              "price": 25.50,
              "stock": 12
            }
            """)
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
        .jsonPath("$.id").isEqualTo(5)
        .jsonPath("$.name").isEqualTo("Mouse");
  }

  @Test
  void rejectsInvalidCreatePayload() {
    webTestClient.post()
        .uri("/api/v1/products")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("""
            {
              "name": "",
              "price": 0,
              "stock": -1
            }
            """)
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody()
        .jsonPath("$.status").isEqualTo(400);
  }

  @Test
  void updatesProduct() {
    when(service.update(any(Long.class), any(ProductRequest.class))).thenReturn(
        Mono.just(product(1L, "Updated")));

    webTestClient.put()
        .uri("/api/v1/products/1")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("""
            {
              "name": "Updated",
              "description": "Updated description",
              "price": 30.00,
              "stock": 3
            }
            """)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.name").isEqualTo("Updated");
  }

  @Test
  void deletesProduct() {
    when(service.delete(1L)).thenReturn(Mono.empty());

    webTestClient.delete()
        .uri("/api/v1/products/1")
        .exchange()
        .expectStatus().isNoContent()
        .expectBody().isEmpty();
  }

  private Product product(Long id, String name) {
    return new Product(id, name, "Description", BigDecimal.TEN, 4, NOW, NOW);
  }
}
