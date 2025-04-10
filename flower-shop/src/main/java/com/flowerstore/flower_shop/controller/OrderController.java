package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.dto.CheckoutRequestDTO;
import com.flowerstore.flower_shop.model.Order;
import com.flowerstore.flower_shop.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final IOrderService iOrderService;

    public OrderController(IOrderService iOrderService) {
        this.iOrderService = iOrderService;
    }

    @Operation(
            summary = "Fetch all orders",
            description = "Returns a list of all orders from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all orders")
    })
    @GetMapping
    public ResponseEntity findAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(iOrderService.getAllOrders());
    }

    @Operation(
            summary = "Create a new order",
            description = "This method adds a new order to the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added a new order."),
            @ApiResponse(responseCode = "400", description = "Invalid input data.")
    })
    @PostMapping
    public ResponseEntity saveNewOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.OK).body(iOrderService.addOrder(order));
    }

    @Operation(
            summary = "Fetch an order by ID",
            description = "Returns an order based on the provided order ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found."),
            @ApiResponse(responseCode = "404", description = "Order not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity getOrder(@Parameter(description = "The ID of the order to fetch", required = true) @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iOrderService.getOrderById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Update an order",
            description = "This method allows updating the details of an existing order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the order."),
            @ApiResponse(responseCode = "400", description = "Invalid order data.")
    })
    @PutMapping
    public ResponseEntity updateOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.OK).body(iOrderService.updateOrder(order));
    }

    @Operation(
            summary = "Delete an order",
            description = "This method deletes the order with the given ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the order."),
            @ApiResponse(responseCode = "404", description = "Order not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@Parameter(description = "The ID of the order to delete", required = true) @PathVariable Long id) {
        iOrderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("successful operation");
    }

    @Operation(
            summary = "Checkout process",
            description = "This method processes the checkout for a user and sends an email confirmation."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order processed successfully and email sent."),
            @ApiResponse(responseCode = "400", description = "Invalid user ID or other error during checkout.")
    })
    @PostMapping("/checkout")
    //primeste cerere de checkout de la frontend(detaliile comenzii), iar backend trimite un raspuns
    public  ResponseEntity<Map<String, Object>> checkout(@RequestBody CheckoutRequestDTO checkoutRequest) {
        try {
            Order order = iOrderService.processCheckout(checkoutRequest);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("orderId", order.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);        }
    }
}
