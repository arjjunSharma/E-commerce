package com.ecommerce.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.product.entity.Category;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  List<Category> findByParentId(Long parentId);

  boolean existsByName(String name);

}
