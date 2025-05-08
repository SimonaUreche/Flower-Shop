package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.dto.PaymentDTO;
import com.flowerstore.flower_shop.mapper.PaymentMapper;
import com.flowerstore.flower_shop.model.Order;
import com.flowerstore.flower_shop.model.Payment;
import com.flowerstore.flower_shop.service.IOrderService;
import com.flowerstore.flower_shop.service.IPaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final IPaymentService paymentService;
    private final IOrderService orderService;

    public PaymentController(IPaymentService paymentService, IOrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentService.getAllPayments().stream()
                .map(PaymentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(PaymentMapper.toDTO(paymentService.getPaymentById(id)));
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO dto) {
        Order order = orderService.getOrderById(dto.getOrderId());
        Payment saved = paymentService.addPayment(PaymentMapper.toEntity(dto, order));
        return ResponseEntity.ok(PaymentMapper.toDTO(saved));
    }

    @PutMapping
    public ResponseEntity<PaymentDTO> updatePayment(@RequestBody PaymentDTO dto) {
        Order order = orderService.getOrderById(dto.getOrderId());
        Payment updated = paymentService.updatePayment(PaymentMapper.toEntity(dto, order));
        return ResponseEntity.ok(PaymentMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Payment method deleted.");
    }
}
