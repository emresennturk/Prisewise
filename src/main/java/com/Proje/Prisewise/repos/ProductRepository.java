package com.Proje.Prisewise.repos;

import com.Proje.Prisewise.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByUrunAdiContainingIgnoreCase(String keyword);
    boolean existsByUniqueKey(String uniqueKey);

    @Query(value = "SELECT * FROM urunler ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Product> findRandomProducts(@Param("limit") int limit);
}
