package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.model.CartItem;
import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.service.ICartItemService;
import com.flowerstore.flower_shop.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/cartItem")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final IUserService userService;

    public CartItemController(ICartItemService cartItemService, IUserService userService) {
        this.cartItemService = cartItemService;
        this.userService = userService;
    }

    @Operation(summary = "Fetch all cart items", description = "This method returns all cart items from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully returned all cart items.")
    @GetMapping
    public ResponseEntity<List<CartItem>> findAllCartItems() {
        return ResponseEntity.status(HttpStatus.OK).body(cartItemService.getAllCartItems());
    }

    @Operation(summary = "Fetch all cart items for a specific user", description = "This method returns all cart items for a given user based on the user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned the user's cart items."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItem>> findCartItemsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(cartItemService.getCartItemsByUserId(userId));
    }

    @Operation(summary = "Add a new cart item", description = "This method adds a new item to the cart for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added the cart item."),
            @ApiResponse(responseCode = "400", description = "Invalid data provided.")
    })
    @PostMapping
    public ResponseEntity<String> saveNewCartItem(@RequestBody CartItem cartItem) {
        Long userId = cartItem.getUser().getId();
        User user = userService.getUserById(userId);
        cartItem.setUser(user);
        cartItemService.addCartItem(cartItem);
        return ResponseEntity.status(HttpStatus.OK).body("Adăugat în coș");
    }

    @Operation(summary = "Fetch a cart item by ID", description = "This method returns the cart item for the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned the cart item."),
            @ApiResponse(responseCode = "404", description = "Cart item not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItem(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(cartItemService.getCartItemById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Update a cart item", description = "This method allows updating the details of an existing cart item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the cart item."),
            @ApiResponse(responseCode = "400", description = "Invalid data provided.")
    })
    @PutMapping
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem) {
        return ResponseEntity.status(HttpStatus.OK).body(cartItemService.updateCartItem(cartItem));
    }

    @Operation(summary = "Delete a cart item", description = "This method deletes the cart item with the specified ID from the cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the cart item."),
            @ApiResponse(responseCode = "404", description = "Cart item not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("successful operation");
    }

    @Operation(summary = "Clear all cart items for a user", description = "This method removes all items from the cart of the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully cleared the user's cart."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        cartItemService.clearCart(userId);
        return ResponseEntity.ok("Coș golit");
    }
}
