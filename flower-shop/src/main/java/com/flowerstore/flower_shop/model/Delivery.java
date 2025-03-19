package com.flowerstore.flower_shop.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Delivery {
    private Long id;
    private Order order;
    private User deliverer;
    private String deliveryStatus; // Dispatched, In Transit, Delivered
}
