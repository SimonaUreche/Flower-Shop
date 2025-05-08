package com.flowerstore.flower_shop.mapper;

import com.flowerstore.flower_shop.dto.CartItemDTO;
import com.flowerstore.flower_shop.dto.ProductDTO;
import com.flowerstore.flower_shop.model.CartItem;
import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.model.Product;

public class CartItemMapper {
    public static CartItemDTO toDTO(CartItem cartItem) {
        Product product = cartItem.getProduct();

        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .image(product.getImage())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();

        return CartItemDTO.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .product(productDTO)
                .build();
    }

    public static CartItem toEntity(CartItemDTO dto, User user, Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setId(dto.getId());
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(dto.getQuantity());
        return cartItem;
    }
}
