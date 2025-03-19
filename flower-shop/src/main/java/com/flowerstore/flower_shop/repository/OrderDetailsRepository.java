package com.flowerstore.flower_shop.repository;

import com.flowerstore.flower_shop.model.OrderDetails;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Repository
public class OrderDetailsRepository {
    private final List<OrderDetails> orderDetails = new ArrayList<>();
    private Long nextId = 1L;

    public OrderDetails save(OrderDetails orderDetail) {
        if (orderDetail.getId() == null) {
            orderDetail.setId(nextId++);
        }
        orderDetails.add(orderDetail);
        return orderDetail;
    }

    public List<OrderDetails> findAll() {
        return orderDetails;
    }

    public OrderDetails findByOrderId(Long orderId) {
        return orderDetails.stream().filter(orderDetails -> orderDetails.getId().equals(orderId)).findFirst().get();
    }

    public OrderDetails updateOrderDetails(Long id, int newQuantity) {
        OrderDetails oldOrderDetails = (OrderDetails) findByOrderId(id);

        if (oldOrderDetails == null) {
            oldOrderDetails.setQuantity(newQuantity);
            oldOrderDetails.setSubtotal(oldOrderDetails.getPriceAtOrder()*newQuantity);
            return oldOrderDetails;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return orderDetails.removeIf(order -> order.getId().equals(id));
    }

}