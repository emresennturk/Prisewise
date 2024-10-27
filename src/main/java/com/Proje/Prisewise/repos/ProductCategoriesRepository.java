package com.Proje.Prisewise.repos;

import com.Proje.Prisewise.entities.Category;

import com.Proje.Prisewise.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoriesRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByCategory(Category category);
}
