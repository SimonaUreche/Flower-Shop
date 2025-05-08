package com.flowerstore.flower_shop.dto;

import com.flowerstore.flower_shop.model.OrderDetails;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;
    private String status;
    private double totalPrice;
    private List<OrderDetailsDTO> orderDetails;

}
