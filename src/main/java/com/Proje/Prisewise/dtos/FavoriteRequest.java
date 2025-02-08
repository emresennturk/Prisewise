package com.Proje.Prisewise.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteRequest {
    private String userId;
    private Long urunId;
}
