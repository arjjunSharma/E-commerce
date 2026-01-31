package com.ecommerce.product.service;

import java.util.List;

import com.ecommerce.product.dto.CategoryDto;

public interface CategoryService {

  CategoryDto createCategory(CategoryDto categoryDto);

  CategoryDto updateCategory(Long id, CategoryDto categoryDto);

  void deleteCategory(Long id);

  CategoryDto getCategory(Long id);

  List<CategoryDto> getAllCategories();
}
