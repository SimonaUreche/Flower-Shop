package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.CartItem;
import com.flowerstore.flower_shop.repository.CartItemRepository;
import com.flowerstore.flower_shop.service.ICartItemService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements ICartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItem addCartItem(CartItem cartItem) {
        Optional<CartItem> optionalItem = cartItemRepository.findByUserIdAndProductId(
                cartItem.getUser().getId(), cartItem.getProduct().getId()
        );

        if (optionalItem.isPresent()) {
            CartItem existingItem = optionalItem.get();

            int totalQuantity = existingItem.getQuantity() + cartItem.getQuantity();

            if (totalQuantity > cartItem.getProduct().getStock()) {
                throw new IllegalArgumentException("Quantity exceeds available stock");
            }

            existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
            return cartItemRepository.save(existingItem);
        } else {
            if (cartItem.getQuantity() > cartItem.getProduct().getStock()) {
                throw new IllegalArgumentException("Quantity exceeds available stock");
            }
            return cartItemRepository.save(cartItem);
        }
    }

    @Override
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @Override
    public CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cart item with id " + id + " not found"));
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        CartItem existing = cartItemRepository.findById(cartItem.getId())
                .orElseThrow(() -> new NoSuchElementException("Cart item not found"));

        if (cartItem.getQuantity() > cartItem.getProduct().getStock()) {
            throw new IllegalArgumentException("Quantity exceeds available stock");
        }

        existing.setQuantity(cartItem.getQuantity());
        return cartItemRepository.save(existing);
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
    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
