package com.Proje.Prisewise.controllers;

import com.Proje.Prisewise.entities.User;
import com.Proje.Prisewise.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email zaten bulunuyor.");
        }
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody User user) {
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email zaten bulunuyor.");
        }
        return ResponseEntity.ok(userService.saveGoogleUser(user));
    }
}
