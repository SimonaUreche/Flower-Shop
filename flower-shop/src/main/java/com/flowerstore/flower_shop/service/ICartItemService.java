package com.flowerstore.flower_shop.service;

import com.flowerstore.flower_shop.model.CartItem;

import java.util.List;

public interface ICartItemService {
    CartItem addCartItem(CartItem cartItem);
    List<CartItem> getAllCartItems();
    CartItem getCartItemById(Long id);
    CartItem updateCartItem(CartItem cartItem);
    void deleteCartItem(Long id);
    List<CartItem> getCartItemsByUserId(Long userId);
    void clearCart(Long userId);
}
