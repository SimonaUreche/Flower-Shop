package com.flowerstore.flower_shop.dto;

import lombok.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequestDTO {
    private Long userId;
    private String fullName;
    private String email;
    private String address;
    private List<OrderDetailDTO> orderDetails;
}
