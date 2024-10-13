package com.Proje.Prisewise.repos;

import com.Proje.Prisewise.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByUrunAdiContainingIgnoreCase(String keyword);
    boolean existsByUniqueKey(String uniqueKey);
}
