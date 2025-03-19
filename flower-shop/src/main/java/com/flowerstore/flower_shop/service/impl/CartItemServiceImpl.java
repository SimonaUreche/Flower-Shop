package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.CartItem;
import com.flowerstore.flower_shop.repository.CartItemRepository;
import com.flowerstore.flower_shop.service.ICartItemService;

import java.util.List;
import java.util.NoSuchElementException;

public class CartItemServiceImpl implements ICartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }
    @Override
    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @Override
    public CartItem getCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id);
        if(cartItem != null){
            return cartItem;
        }
        throw new NoSuchElementException("Cart Item with id " + id+ "not found");
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        return cartItemRepository.updateCartItem(cartItem);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}
