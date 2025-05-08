package com.flowerstore.flower_shop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDTO {
    private Long id;
    private String code;
    private double discount;
    private boolean isActive;
}
