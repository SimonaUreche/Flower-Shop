package com.flowerstore.flower_shop.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    private int quantity; //Cantitatea produsului în comandă
    private double priceAtOrder; //Pretul produsului in momentul comenzii(in cazul in care apare un anumit discount)
    private double subtotal; // quantity * priceAtOrder

}
