package com.challenge.products.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductRequest(
    @NotBlank(message = "name is required")
    @Size(max = 120, message = "name must have 120 characters or fewer")
    String name,

    @Size(max = 500, message = "description must have 500 characters or fewer")
    String description,

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.01", message = "price must be greater than zero")
    BigDecimal price,

    @NotNull(message = "stock is required")
    @Min(value = 0, message = "stock must be zero or greater")
    Integer stock
) {
}
