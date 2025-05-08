package com.flowerstore.flower_shop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusNotification {
    private Long orderId;
    private String status;

}
