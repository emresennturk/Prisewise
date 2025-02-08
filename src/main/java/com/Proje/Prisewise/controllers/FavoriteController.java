package com.Proje.Prisewise.controllers;

import com.Proje.Prisewise.entities.Favorite;
import com.Proje.Prisewise.dtos.FavoriteRequest;
import com.Proje.Prisewise.entities.Product;
import com.Proje.Prisewise.entities.User;
import com.Proje.Prisewise.repos.FavoriteRepository;
import com.Proje.Prisewise.repos.ProductRepository;
import com.Proje.Prisewise.repos.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public FavoriteController(FavoriteRepository favoriteRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        if (!userRepository.existsById(favoriteRequest.getUserId())) {
            return ResponseEntity.badRequest().body("Kullanıcı adı bulunamadı.");
        }
        if (!productRepository.existsById(favoriteRequest.getUrunId())) {
            return ResponseEntity.badRequest().body("Ürün bulunamadı.");
        }
        if (favoriteRepository.existsByUserIdAndProductId(favoriteRequest.getUserId(), favoriteRequest.getUrunId())) {
            return ResponseEntity.badRequest().body("Bu ürün zaten favorilerde bulunuyor.");
        }

        User user = userRepository.findById(favoriteRequest.getUserId()).orElseThrow();
        Product product = productRepository.findById(favoriteRequest.getUrunId()).orElseThrow();

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);

        favoriteRepository.save(favorite);

        return ResponseEntity.ok("Ürün favorilere eklendi");
    }

    @DeleteMapping("/remove")
    @Transactional
    public ResponseEntity<String> removeFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        if (!favoriteRepository.existsByUserIdAndProductId(favoriteRequest.getUserId(), favoriteRequest.getUrunId())) {
            return ResponseEntity.badRequest().body("Bu urun favorilerde bulunmamaktadir.");
        }
        favoriteRepository.deleteByUserIdAndProductId(favoriteRequest.getUserId(), favoriteRequest.getUrunId());

        return ResponseEntity.ok("Urun favorilerden cikarildi.");
    }

    @PostMapping("/list")
    public ResponseEntity<List<Product>> getFavorites(@RequestBody FavoriteRequest favoriteRequest) {
        if (!userRepository.existsById(favoriteRequest.getUserId())) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Favorite> favorites = favoriteRepository.findByUserId(favoriteRequest.getUserId());
        List<Product> products = favorites.stream()
                .map(Favorite::getProduct)
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

}
