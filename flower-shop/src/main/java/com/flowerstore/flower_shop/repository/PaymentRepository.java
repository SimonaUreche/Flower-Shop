package com.flowerstore.flower_shop.repository;

import com.flowerstore.flower_shop.model.OrderDetails;
import com.flowerstore.flower_shop.model.Payment;
import com.flowerstore.flower_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//@Repository
//public class PaymentRepository {
//    private final List<Payment> paymentList = new ArrayList<>();
//    private Long nextId = 1L;
//
//    public Payment save(Payment payment) {
//        if(payment.getId() == null) {
//            payment.setId(nextId++);
//        }
//        paymentList.add(payment);
//        return payment;
//    }
//
//    public List<Payment> findAll() {
//        return paymentList;
//    }
//
//    public Payment findById(Long id) {
//        return paymentList.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
//    }
//
//    public Payment updatePayment(Payment payment) {
//        Payment oldPayment = findById(payment.getId());
//
//        if(oldPayment != null){
//            oldPayment.setOrder(payment.getOrder());
//            oldPayment.setPaymentMethod(payment.getPaymentMethod());
//            oldPayment.setPaid(payment.isPaid());
//            return oldPayment;
//        }
//        return null;
//    }
//
//    public boolean deleteById(Long id) {
//        return paymentList.removeIf(product -> product.getId().equals(id));
//    }
//
//}

public interface PaymentRepository extends JpaRepository<Payment, Long> {}