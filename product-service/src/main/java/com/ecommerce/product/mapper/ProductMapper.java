package com.ecommerce.product.mapper;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.Product;

public class ProductMapper {
  public ProductDto toDto(Product product) {
    return ProductDto.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .discountPrice(product.getDiscountPrice())
        .quantity(product.getQuantity())
        .imageUrl(product.getImageUrl())
        .brand(product.getBrand())
        .categoryId(product.getCategory().getId())
        .build();
  }

  public Product toEntity(ProductDto productDto, Category category) {
    return Product.builder()
        .id(productDto.getId())
        .name(productDto.getName())
        .description(productDto.getDescription())
        .price(productDto.getPrice())
        .discountPrice(productDto.getDiscountPrice())
        .quantity(productDto.getQuantity())
        .imageUrl(productDto.getImageUrl())
        .brand(productDto.getBrand())
        .build();
  }
}