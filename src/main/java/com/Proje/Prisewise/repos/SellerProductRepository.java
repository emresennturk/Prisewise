package com.Proje.Prisewise.repos;

import com.Proje.Prisewise.entities.Product;
import com.Proje.Prisewise.entities.SellerProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SellerProductRepository extends JpaRepository<SellerProduct, Long> {
    List<SellerProduct> findByProduct(Product product);
}
