package com.challenge.products.exception;

import com.challenge.products.dto.ErrorResponse;
import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(ProductNotFoundException exception,
                                                      ServerWebExchange exchange) {
    return build(HttpStatus.NOT_FOUND, exception.getMessage(), exchange);
  }

  @ExceptionHandler({InvalidProductException.class, WebExchangeBindException.class})
  public ResponseEntity<ErrorResponse> handleBadRequest(Exception exception,
                                                        ServerWebExchange exchange) {
    String message = exception instanceof WebExchangeBindException bindException
        ? bindException.getFieldErrors().stream()
        .map(error -> "%s: %s".formatted(error.getField(), error.getDefaultMessage()))
        .collect(Collectors.joining("; "))
        : exception.getMessage();

    return build(HttpStatus.BAD_REQUEST, message, exchange);
  }

  private ResponseEntity<ErrorResponse> build(HttpStatus status, String message,
                                              ServerWebExchange exchange) {
    ErrorResponse body = new ErrorResponse(
        Instant.now(),
        status.value(),
        status.getReasonPhrase(),
        message,
        exchange.getRequest().getPath().value()
    );
    return ResponseEntity.status(status).body(body);
  }
}
