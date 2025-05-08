package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.dto.CheckoutRequestDTO;
import com.flowerstore.flower_shop.dto.OrderDTO;
import com.flowerstore.flower_shop.dto.OrderStatusNotification;
import com.flowerstore.flower_shop.mapper.OrderMapper;
import com.flowerstore.flower_shop.model.Order;
import com.flowerstore.flower_shop.model.OrderDetails;
import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.service.IOrderDetailsService;
import com.flowerstore.flower_shop.service.IOrderService;
import com.flowerstore.flower_shop.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrderController {
    private final IOrderService orderService;
    private final IUserService userService;
    private final IOrderDetailsService orderDetailsService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public OrderController(IOrderService orderService, IUserService userService, IOrderDetailsService orderDetailsService) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderDetailsService = orderDetailsService;
    }

    @Operation(summary = "Fetch all orders", description = "This method returns all orders from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully returned all orders.")
    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAllOrders() {
        List<OrderDTO> dtos = orderService.getAllOrders().stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }


    @Operation(summary = "Fetch all orders for a specific user", description = "This method returns all orders for a given user based on the user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned the user's orders."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> findOrdersByUser(@PathVariable Long userId) {
        List<OrderDTO> dtos = orderService.getOrdersByUserId(userId).stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }



    @Operation(summary = "Fetch an order by ID", description = "This method returns the order for the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned the order."),
            @ApiResponse(responseCode = "404", description = "Order not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        try {
            Order order = orderService.getOrderById(id);
            return ResponseEntity.ok(OrderMapper.toDTO(order));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Place an order (checkout)", description = "Creates an order from the shopping cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully processed checkout."),
            @ApiResponse(responseCode = "400", description = "Invalid request."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> checkout(@RequestBody CheckoutRequestDTO checkoutRequest) {
        try {
            Order order = orderService.processCheckout(checkoutRequest);
            System.out.println(checkoutRequest);
            return ResponseEntity.ok(OrderMapper.toDTO(order));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Operation(summary = "Delete an order", description = "This method deletes the order with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the order."),
            @ApiResponse(responseCode = "404", description = "Order not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        try {
            orderService.getOrderById(id);
            orderService.deleteOrder(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Order deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }

    }

    @Operation(summary = "Update order status", description = "This method updates the status of an existing order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the order status."),
            @ApiResponse(responseCode = "404", description = "Order not found.")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Order updated = orderService.updateOrderStatus(id, status);

            OrderStatusNotification notification = new OrderStatusNotification(updated.getId(), updated.getStatus());
            messagingTemplate.convertAndSend("/topic/order-status", notification);

            return ResponseEntity.ok(OrderMapper.toDTO(updated));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}