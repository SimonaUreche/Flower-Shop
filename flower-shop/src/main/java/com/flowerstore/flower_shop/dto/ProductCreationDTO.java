package com.flowerstore.flower_shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreationDTO {
    private String name;
    private String description;
    private double price;
    private int stock;
    private String image;
    private Long categoryId;
}
