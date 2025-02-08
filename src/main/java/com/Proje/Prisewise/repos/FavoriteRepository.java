package com.Proje.Prisewise.repos;

import com.Proje.Prisewise.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(String userId);

    boolean existsByUserIdAndProductId(String userId, Long urunId);

    void deleteByUserIdAndProductId(String userId, Long urunId);
}
