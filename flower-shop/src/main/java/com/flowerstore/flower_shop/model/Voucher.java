package com.flowerstore.flower_shop.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Voucher {
    private Long id;
    private String code;
    private double discount;
    private boolean isActive;
}
