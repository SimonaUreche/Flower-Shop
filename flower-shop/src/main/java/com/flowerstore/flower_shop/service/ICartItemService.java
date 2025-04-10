package com.flowerstore.flower_shop.service;

import com.flowerstore.flower_shop.model.CartItem;
import com.flowerstore.flower_shop.model.Order;

import java.util.List;

public interface ICartItemService {
    CartItem addCartItem(CartItem cartItem);

    List<CartItem> getAllCartItems();
    CartItem getCartItemById(Long id);
    CartItem updateCartItem(CartItem cartItem);
    void deleteCartItem(Long id);
    public List<CartItem> getCartItemsByUserId(Long userId);
    public void clearCart(Long userId);
}
