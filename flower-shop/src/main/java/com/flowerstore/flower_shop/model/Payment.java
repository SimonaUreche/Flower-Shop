package com.flowerstore.flower_shop.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Payment {
    private Long id;
    private Order order;
    private String paymentMethod; //Card, PayPal, Cash
    private boolean isPaid;
}