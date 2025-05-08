package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.dto.CartItemDTO;
import com.flowerstore.flower_shop.dto.ProductDTO;
import com.flowerstore.flower_shop.mapper.CartItemMapper;
import com.flowerstore.flower_shop.model.CartItem;
import com.flowerstore.flower_shop.model.Product;
import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.service.ICartItemService;
import com.flowerstore.flower_shop.service.IProductService;
import com.flowerstore.flower_shop.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/cartItem")
public class CartItemController {

    private final ICartItemService cartItemService;
    private final IUserService userService;
    private final IProductService productService;

    public CartItemController(ICartItemService cartItemService, IUserService userService, IProductService productService) {
        this.cartItemService = cartItemService;
        this.userService = userService;
        this.productService = productService;
    }

    @Operation(summary = "Fetch all cart items")
    @ApiResponse(responseCode = "200", description = "Successfully returned all cart items.")
    @GetMapping
    public ResponseEntity<List<CartItemDTO>> findAllCartItems() {
        List<CartItemDTO> dtos = cartItemService.getAllCartItems().stream()
                .map(CartItemMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Fetch cart items by user ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully returned user's cart items."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItemDTO>> findCartItemsByUser(@PathVariable Long userId) {
        List<CartItemDTO> dtos = cartItemService.getCartItemsByUserId(userId).stream()
                .map(CartItemMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Add a new cart item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully added the cart item."),
            @ApiResponse(responseCode = "400", description = "Invalid data provided.")
    })
    @PostMapping
    public ResponseEntity<String> saveNewCartItem(@RequestBody CartItemDTO cartItemDTO) {
        try {

            Long userId = cartItemDTO.getUserId() == 0 ? 0L : cartItemDTO.getUserId();
            User user = userService.getUserById(userId);

            ProductDTO productDTO = cartItemDTO.getProduct();
            if (productDTO == null || productDTO.getId() == null) {
                throw new IllegalArgumentException("Product ID is missing");
            }

            Product product = productService.getProductById(productDTO.getId());
            CartItem cartItem = CartItemMapper.toEntity(cartItemDTO, user, product);
            cartItemService.addCartItem(cartItem);
            return ResponseEntity.ok("Adăugat în coș");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizator sau produs inexistent.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date invalide: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Eroare la adăugarea în coș: " + e.getMessage());
        }
    }

    @Operation(summary = "Fetch a cart item by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully returned the cart item."),
            @ApiResponse(responseCode = "404", description = "Cart item not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CartItemDTO> getCartItem(@PathVariable Long id) {
        try {
            CartItem cartItem = cartItemService.getCartItemById(id);
            return ResponseEntity.ok(CartItemMapper.toDTO(cartItem));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Update a cart item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the cart item."),
            @ApiResponse(responseCode = "400", description = "Invalid data provided.")
    })
    @PutMapping
    public ResponseEntity<CartItemDTO> updateCartItem(@RequestBody CartItemDTO cartItemDTO) {
        try {
            User user = userService.getUserById(cartItemDTO.getUserId());

            ProductDTO productDTO = cartItemDTO.getProduct();
            if (productDTO == null || productDTO.getId() == null) {
                throw new IllegalArgumentException("Product ID is missing");
            }

            Product product = productService.getProductById(productDTO.getId());
            CartItem updated = cartItemService.updateCartItem(CartItemMapper.toEntity(cartItemDTO, user, product));
            return ResponseEntity.ok(CartItemMapper.toDTO(updated));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "Delete a cart item by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted the cart item."),
            @ApiResponse(responseCode = "404", description = "Cart item not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long id) {
        try {
            cartItemService.getCartItemById(id); // Verificăm dacă există
            cartItemService.deleteCartItem(id);
            return ResponseEntity.ok("Cart item deleted successfully.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found.");
        }
    }

    @Operation(summary = "Clear all cart items for a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully cleared the user's cart."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        try {
            cartItemService.getCartItemsByUserId(userId); // verificare existență
            cartItemService.clearCart(userId);
            return ResponseEntity.ok("Cart items deleted successfully.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart items not found.");
        }
    }
}
