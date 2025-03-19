package com.flowerstore.flower_shop.model;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

//fiecare articol din cosul unui utilizator
public class CartItem { //la checkout, toate CartItem sunt convertite in OrderDetail, iar cosul e golit
    private Long id;
    private User user;
    private Product product;
    private int quantity;
}
