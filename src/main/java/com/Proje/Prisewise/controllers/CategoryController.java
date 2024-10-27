package com.Proje.Prisewise.controllers;

import com.Proje.Prisewise.entities.Category;
import com.Proje.Prisewise.entities.Product;
import com.Proje.Prisewise.entities.ProductCategory;
import com.Proje.Prisewise.exceptions.ResourceNotFoundException;
import com.Proje.Prisewise.repos.CategoryRepository;
import com.Proje.Prisewise.repos.ProductCategoriesRepository;
import com.Proje.Prisewise.repos.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final ProductCategoriesRepository productCategoriesRepository;

    public CategoryController(CategoryRepository categoryRepository, ProductCategoriesRepository productCategoriesRepository) {
        this.categoryRepository = categoryRepository;
        this.productCategoriesRepository = productCategoriesRepository;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));

        // Fetch ProductCategory entities associated with the given category
        List<ProductCategory> productCategory = productCategoriesRepository.findByCategory(category);

        // Map ProductCategory to Product
        List<Product> products = productCategory.stream()
                .map(ProductCategory::getProduct)
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }
}
