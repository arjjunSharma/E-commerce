package com.ecommerce.product.mapper;

import com.ecommerce.product.dto.CategoryDto;
import com.ecommerce.product.entity.Category;

public class CategoryMapper {
  public CategoryDto toDto(Category category) {
    return CategoryDto.builder()
        .id(category.getId())
        .name(category.getName())
        .description(category.getDescription())
        .parentId(category.getParentId())
        .build();
  }

  public Category toEntity(CategoryDto categoryDto) {
    return Category.builder()
        .id(categoryDto.getId())
        .name(categoryDto.getName())
        .description(categoryDto.getDescription())
        .parentId(categoryDto.getParentId())
        .build();
  }

}
