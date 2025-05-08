package com.flowerstore.flower_shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeliveryDTO {
    private Long id;
    private Long orderId;
    private Long delivererId;
    private String deliveryStatus;
}
