package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.CartItem;
import com.flowerstore.flower_shop.repository.CartItemRepository;
import com.flowerstore.flower_shop.service.ICartItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CartItemServiceImpl implements ICartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItem addCartItem(CartItem cartItem) {
        CartItem existingItem = cartItemRepository.findByUserIdAndProductId(
                cartItem.getUser().getId(), cartItem.getProduct().getId()
        );

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
            return existingItem;
        } else {
            return cartItemRepository.save(cartItem);
        }
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

    @Override
    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Override
    public void clearCart(Long userId) {
        cartItemRepository.clearByUserId(userId);
    }
}
