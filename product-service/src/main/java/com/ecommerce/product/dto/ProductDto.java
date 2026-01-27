package com.ecommerce.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
  Long id;
  @NotBlank(message = "Product name cannot be empty ")
  private String name;
  @NotBlank(message = "Product description cannot be empty ")
  private String description;
  @Positive(message = "Price cannot be negative")
  private double price;
  private double discountPrice;
  @Min(value = 0, message = "Quantity cannot be negative")
  private int quantity;
  private String brand;
  private String imageUrl;
  @NotNull(message = "Category ID cannot be null")
  private Long categoryId;

}
