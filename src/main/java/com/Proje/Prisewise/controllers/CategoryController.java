package com.Proje.Prisewise.controllers;

import com.Proje.Prisewise.dtos.ProductDTO;
import com.Proje.Prisewise.dtos.SellerDTO;
import com.Proje.Prisewise.entities.Category;
import com.Proje.Prisewise.entities.Product;
import com.Proje.Prisewise.entities.ProductCategory;
import com.Proje.Prisewise.exceptions.ResourceNotFoundException;
import com.Proje.Prisewise.repos.CategoryRepository;
import com.Proje.Prisewise.repos.ProductCategoriesRepository;
import com.Proje.Prisewise.repos.SellerProductRepository;
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
    private final SellerProductRepository sellerProductRepository;

    public CategoryController(CategoryRepository categoryRepository, ProductCategoriesRepository productCategoriesRepository, SellerProductRepository sellerProductRepository) {
        this.categoryRepository = categoryRepository;
        this.productCategoriesRepository = productCategoriesRepository;
        this.sellerProductRepository = sellerProductRepository;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(@PathVariable Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));

        List<ProductCategory> productCategories = productCategoriesRepository.findByCategory(category);

        List<ProductDTO> productDTOs = productCategories.stream()
                .map(pc -> {
                    Product product = pc.getProduct();
                    ProductDTO dto = new ProductDTO();
                    dto.setId(product.getId());
                    dto.setUrunAdi(product.getUrunAdi());
                    dto.setFiyat(product.getFiyat());
                    dto.setUrl(product.getUrl());
                    dto.setResim_url(product.getResim_url());

                    // Gelen ürünler için satıcı bilgisi çekiliyor
                    List<SellerDTO> sellerDTOs = sellerProductRepository.findByProduct(product)
                            .stream()
                            .map(sellerProduct -> {
                                SellerDTO sellerDTO = new SellerDTO();
                                sellerDTO.setId(sellerProduct.getSeller().getId());
                                sellerDTO.setSaticiAdi(sellerProduct.getSeller().getSaticiAdi());
                                return sellerDTO;
                            })
                            .collect(Collectors.toList());

                    dto.setSellers(sellerDTOs);
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDTOs);
    }
}
