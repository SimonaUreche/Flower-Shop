package com.flowerstore.flower_shop.service;

import com.flowerstore.flower_shop.model.Payment;

import java.util.List;

public interface IPaymentService {
    Payment addPayment(Payment payment);
    List<Payment> getAllPayments();
    Payment getPaymentById(Long id);
    Payment updatePayment(Payment payment);
    void deletePayment(Long id);
}
