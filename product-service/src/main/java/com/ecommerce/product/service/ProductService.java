package com.ecommerce.product.service;

import org.springframework.data.domain.Page;

import com.ecommerce.product.dto.ProductDto;

public interface ProductService {
    ProductDto createProduct(ProductDto dto);

    ProductDto updateProduct(Long id, ProductDto dto);

    void deleteProduct(Long id);

    ProductDto getProductById(Long id);

    Page<ProductDto> getAllProducts(
            int page,
            int size, String sortBy, String sortDir);

    Page<ProductDto> searchProducts(
            String keyword,
            int page,
            int size);

    Page<ProductDto> filterProducts(
            String keyword,
            Long categoryId,
            Double minPrice,
            Double maxPrice,
            int page,
            int size);

    Page<ProductDto> advanceFilter(
            String keyword,
            Long categoryId,
            Double minPrice,
            Double maxPrice,
            int page,
            int size,
            String sortBy,
            String sortDir);
}
