package com.Proje.Prisewise.services;

import com.Proje.Prisewise.entities.User;
import com.Proje.Prisewise.repos.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return (User) userRepository.save(user);
    }
}
