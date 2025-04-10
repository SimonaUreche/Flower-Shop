package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.model.OrderDetails;
import com.flowerstore.flower_shop.service.IOrderDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/orderDetails")
public class OrderDetailsController {
    private final IOrderDetailsService IOrderDetailsService;

    public OrderDetailsController(IOrderDetailsService IOrderDetailsService) {
        this.IOrderDetailsService = IOrderDetailsService;
    }

    @GetMapping
    public ResponseEntity findAllOrdersDetails() {
        return ResponseEntity.status(HttpStatus.OK).body(IOrderDetailsService.getAllOrderDetails());
    }

    @PostMapping
    public ResponseEntity saveNewOrderDetails(@RequestBody OrderDetails orderDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(IOrderDetailsService.addOrderDetails(orderDetails));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOrderDetails(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(IOrderDetailsService.getOrderDetailsById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateOrderDetails(@RequestBody OrderDetails orderDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(IOrderDetailsService.updateOrderDetails(orderDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrderDetails(@PathVariable Long id) {
        IOrderDetailsService.deleteOrderDetails(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("successful operation");
    }

}