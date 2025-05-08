package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.Order;
import com.flowerstore.flower_shop.model.Payment;
import com.flowerstore.flower_shop.repository.PaymentRepository;
import com.flowerstore.flower_shop.service.IPaymentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PaymentServiceImpl implements IPaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    @Override
    public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Payment with id" + id + " does not exist"));
    }
    @Override
    public Payment updatePayment(Payment payment) {
        if (!paymentRepository.existsById(payment.getId())) {
            throw new NoSuchElementException("Payment not found for update");
        }
        return paymentRepository.save(payment);
    }
    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
