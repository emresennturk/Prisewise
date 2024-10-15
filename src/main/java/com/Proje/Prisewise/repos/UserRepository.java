package com.Proje.Prisewise.repos;

import com.Proje.Prisewise.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
