package com.flowerstore.flower_shop.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Category {
    private Long id;
    private String name;
}
