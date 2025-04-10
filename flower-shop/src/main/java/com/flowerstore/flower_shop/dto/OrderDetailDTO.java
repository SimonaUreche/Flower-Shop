package com.flowerstore.flower_shop.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class OrderDetailDTO {
    private Long productId;
    private int quantity;
    private double price;

}
