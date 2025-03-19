package com.flowerstore.flower_shop.repository;

import com.flowerstore.flower_shop.model.CartItem;
import com.flowerstore.flower_shop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

    public CartItem updateCartItem(CartItem cartItem) { //pt un cartItem facem update doar pe cantitate
        CartItem oldCarItem = findById(cartItem.getId());

        if(oldCarItem != null){
            oldCarItem.setQuantity(oldCarItem.getQuantity() + cartItem.getQuantity());
            return oldCarItem;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return cartItems.removeIf(product -> product.getId().equals(id));
    }
}
