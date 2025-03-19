package com.flowerstore.flower_shop.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Review {
    private Long id;
    private User user;
    private Product product;
    private int rating;
    private String comment;
}
