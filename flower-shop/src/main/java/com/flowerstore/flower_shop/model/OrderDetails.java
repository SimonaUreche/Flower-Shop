package com.flowerstore.flower_shop.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetails {
    private Long id;
    private Order order;
    private Product product;
    private int quantity; //Cantitatea produsului în comandă
    private double priceAtOrder; //Pretul produsului in momentul comenzii(in cazul in care apare un anumit discount)
    private double subtotal; // quantity * priceAtOrder
}
