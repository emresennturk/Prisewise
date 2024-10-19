package com.Proje.Prisewise.services;

import com.Proje.Prisewise.entities.Product;
import com.Proje.Prisewise.repos.ProductRepository;
import com.Proje.Prisewise.repos.SellerProductRepository;
import com.Proje.Prisewise.repos.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private SellerProductRepository sellerProductRepository;

    public List<Product> getProductsByKeyword(String keyword) {
        return productRepository.findByUrunAdiContainingIgnoreCase(keyword);
    }

    public List<Product> getRandomProducts(int count) {
        return productRepository.findRandomProducts(count);
    }


}
