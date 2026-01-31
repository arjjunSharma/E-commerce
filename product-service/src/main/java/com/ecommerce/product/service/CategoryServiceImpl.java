package com.ecommerce.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.CategoryDto;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.mapper.CategoryMapper;
import com.ecommerce.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public CategoryDto createCategory(CategoryDto categoryDto) {
    Category category = categoryMapper.toEntity(categoryDto);
    Category saved = categoryRepository.save(category);
    return categoryMapper.toDto(saved);
  }

  @Override
  public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
    Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    category.setName(categoryDto.getName());
    category.setDescription(null);
    Category updated = categoryRepository.save(category);
    return categoryMapper.toDto(updated);
  }

  @Override
  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);

  }

  @Override
  public CategoryDto getCategory(Long id) {
    return categoryRepository.findById(id).map(categoryMapper::toDto)
        .orElseThrow(() -> new RuntimeException("Category not found"));
  }

  @Override
  public List<CategoryDto> getAllCategories() {
    return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();

  }
}