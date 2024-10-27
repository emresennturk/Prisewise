package com.Proje.Prisewise.repos;

import com.Proje.Prisewise.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
