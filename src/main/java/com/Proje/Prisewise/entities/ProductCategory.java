package com.Proje.Prisewise.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "urunler_kategoriler")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "urun_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "kategori_id", nullable = false)
    private Category category;
}
