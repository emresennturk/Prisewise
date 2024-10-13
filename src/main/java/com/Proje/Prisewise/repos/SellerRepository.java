package com.Proje.Prisewise.repos;

import com.Proje.Prisewise.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findBySaticiAdi(String saticiAdi);
}
