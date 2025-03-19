package com.flowerstore.flower_shop.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Order {
    private Long id;
    private User user; //se pot realiza comenzi si contul de admin/livrator?
    private List<OrderDetails> orderDetails;
    private double totalPrice; //pt lista de order details
    private String status;
}
