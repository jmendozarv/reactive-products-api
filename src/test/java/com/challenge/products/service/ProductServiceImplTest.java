package com.challenge.products.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.challenge.products.domain.Product;
import com.challenge.products.dto.ProductRequest;
import com.challenge.products.exception.InvalidProductException;
import com.challenge.products.exception.ProductNotFoundException;
import com.challenge.products.repository.ProductRepository;
import com.challenge.products.service.impl.ProductServiceImpl;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ProductServiceImplTest {

  private static final Instant NOW = Instant.parse("2026-06-12T20:00:00Z");

  @Mock
  private ProductRepository repository;

  @Mock
  private Clock clock;

  @InjectMocks
  private ProductServiceImpl service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    when(clock.instant()).thenReturn(NOW);
  }

  @Test
  void createsProductWithNormalizedFields() {
    ProductRequest request =
        new ProductRequest(" Laptop ", " Portable computer ", BigDecimal.valueOf(999.99), 5);
    when(repository.save(any(Product.class))).thenAnswer(
        invocation -> Mono.just(invocation.getArgument(0, Product.class).withId(1L)));

    StepVerifier.create(service.create(request))
        .expectNextMatches(product -> product.id().equals(1L)
            && product.name().equals("Laptop")
            && product.description().equals("Portable computer")
            && product.createdAt().equals(NOW)
            && product.updatedAt().equals(NOW))
        .verifyComplete();
  }

  @Test
  void rejectsBlankName() {
    ProductRequest request = new ProductRequest("   ", "desc", BigDecimal.TEN, 1);

    StepVerifier.create(service.create(request))
        .expectError(InvalidProductException.class)
        .verify();
  }

  @Test
  void findsExistingProduct() {
    Product product = product(10L, "Desk", 3);
    when(repository.findById(10L)).thenReturn(Mono.just(product));

    StepVerifier.create(service.findById(10L))
        .expectNext(product)
        .verifyComplete();
  }

  @Test
  void errorsWhenProductDoesNotExist() {
    when(repository.findById(404L)).thenReturn(Mono.empty());

    StepVerifier.create(service.findById(404L))
        .expectError(ProductNotFoundException.class)
        .verify();
  }

  @Test
  void updatesExistingProductAndKeepsCreationDate() {
    Product existing = product(8L, "Old", 1);
    ProductRequest request = new ProductRequest("New", null, BigDecimal.valueOf(20), 7);
    when(repository.findById(8L)).thenReturn(Mono.just(existing));
    when(repository.save(any(Product.class))).thenAnswer(
        invocation -> Mono.just(invocation.getArgument(0)));

    StepVerifier.create(service.update(8L, request))
        .expectNextMatches(product -> product.id().equals(8L)
            && product.name().equals("New")
            && product.description() == null
            && product.createdAt().equals(existing.createdAt())
            && product.updatedAt().equals(NOW))
        .verifyComplete();
  }

  @Test
  void deletesExistingProduct() {
    Product existing = product(2L, "Chair", 4);
    when(repository.findById(2L)).thenReturn(Mono.just(existing));
    when(repository.delete(existing)).thenReturn(Mono.empty());

    StepVerifier.create(service.delete(2L))
        .verifyComplete();

    verify(repository).delete(existing);
  }

  @Test
  void trimsBlankDescriptionToNullWhenCreating() {
    ProductRequest request = new ProductRequest("Keyboard", "   ", BigDecimal.valueOf(40), 10);
    ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
    when(repository.save(captor.capture())).thenAnswer(
        invocation -> Mono.just(invocation.getArgument(0, Product.class).withId(3L)));

    StepVerifier.create(service.create(request))
        .expectNextMatches(product -> product.description() == null)
        .verifyComplete();
  }

  private Product product(Long id, String name, int stock) {
    return new Product(id, name, "Description", BigDecimal.TEN, stock, NOW.minusSeconds(60),
        NOW.minusSeconds(30));
  }
}
