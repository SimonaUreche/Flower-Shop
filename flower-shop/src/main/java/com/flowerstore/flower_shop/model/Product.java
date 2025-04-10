package com.flowerstore.flower_shop.model;
import com.flowerstore.flower_shop.model.Category;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Product {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String image;
    private Category category;
    private int stock;
}
