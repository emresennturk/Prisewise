package com.Proje.Prisewise.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "urunler")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "urun_adi")
    private String urunAdi;
    private Float fiyat;
    private String url;

    @Column(name = "resim_url")
    private String resim_url;

    @Column(name = "uniqe_key")
    private String uniqueKey;
}
