package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.model.Payment;
import com.flowerstore.flower_shop.service.IPaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final IPaymentService iPaymentService;

    public PaymentController(IPaymentService iPaymentService) {
        this.iPaymentService = iPaymentService;
    }

    @GetMapping
    public ResponseEntity findAllPayments() {
        return ResponseEntity.status(HttpStatus.OK).body(iPaymentService.getAllPayments());
    }

    @PostMapping
    public ResponseEntity saveNewPayment(@RequestBody Payment payment) {
        return ResponseEntity.status(HttpStatus.OK).body(iPaymentService.addPayment(payment));
    }

    @GetMapping("/{id}")
    public ResponseEntity getPayment(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iPaymentService.getPaymentById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updatePayment(@RequestBody Payment payment) {
        return ResponseEntity.status(HttpStatus.OK).body(iPaymentService.updatePayment(payment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePayment(@PathVariable Long id) {
        iPaymentService.deletePayment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("successful operation");
    }

}