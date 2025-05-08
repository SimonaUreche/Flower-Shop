package com.flowerstore.flower_shop.mapper;

import com.flowerstore.flower_shop.dto.PaymentDTO;
import com.flowerstore.flower_shop.model.Order;
import com.flowerstore.flower_shop.model.Payment;

public class PaymentMapper {
    public static PaymentDTO toDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setOrderId(payment.getOrder() != null ? payment.getOrder().getId() : null);
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaid(payment.isPaid());
        return dto;
    }

    public static Payment toEntity(PaymentDTO dto, Order order) {
        return Payment.builder()
                .id(dto.getId())
                .order(order)
                .paymentMethod(dto.getPaymentMethod())
                .isPaid(dto.isPaid())
                .build();
    }
}
