package com.flowerstore.flower_shop.repository;

import com.flowerstore.flower_shop.model.CartItem;
import com.flowerstore.flower_shop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CartItemRepository {
    private final List<CartItem> cartItems = new ArrayList<>();
    private Long nextId = 1L;

    public CartItem save(CartItem cartItem) {
        if(cartItem.getId() == null) {
            cartItem.setId(nextId++);
        }
        cartItems.add(cartItem);
        return cartItem;
    }

    public List<CartItem> findAll() {
        return cartItems;
    }

    public CartItem findById(Long id) {
        return cartItems.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public CartItem updateCartItem(CartItem cartItem) {
        CartItem oldCarItem = findById(cartItem.getId());

        if (oldCarItem != null) {
            Product product = oldCarItem.getProduct();
            if (cartItem.getQuantity() > product.getStock()) {
                throw new IllegalArgumentException("Cantitatea depășește stocul disponibil pentru produsul " + product.getName());
            }

            oldCarItem.setQuantity(cartItem.getQuantity());
            return oldCarItem;
        }
        return null;
    }


    public boolean deleteById(Long id) {
        return cartItems.removeIf(product -> product.getId().equals(id));
    }

    public CartItem findByUserIdAndProductId(Long userId, Long productId) {//metoda pentru cresterea produselor din stoc~verifica daca acelasi produs e deja in cos
        return cartItems.stream()
                .filter(item -> item.getUser() != null && item.getUser().getId().equals(userId)
                        && item.getProduct() != null && item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public List<CartItem> findByUserId(Long userId) {//cosul unui anumit user
        return cartItems.stream()
                .filter(item -> item.getUser() != null && item.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    public void clearByUserId(Long userId) {
        cartItems.removeIf(item -> item.getUser() != null && item.getUser().getId().equals(userId));
    }

}
