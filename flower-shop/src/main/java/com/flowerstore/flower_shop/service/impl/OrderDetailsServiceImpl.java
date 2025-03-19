package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.OrderDetails;
import com.flowerstore.flower_shop.repository.OrderDetailsRepository;
import com.flowerstore.flower_shop.service.IOrderDetailsService;

import java.util.List;
import java.util.NoSuchElementException;

public class OrderDetailsServiceImpl implements IOrderDetailsService {
    private final OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }
    @Override
    public OrderDetails addOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    @Override
    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    @Override
    public OrderDetails getOrderDetailsById(Long id) {
        OrderDetails orderDetails = orderDetailsRepository.findByOrderId(id);
        if (orderDetails != null) {
            return orderDetails;
        }
        throw new NoSuchElementException("OrderDetails with id " + id + "not found");
    }

    @Override
    public OrderDetails updateOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.updateOrderDetails(orderDetails.getId(), orderDetails.getQuantity());
    }

    @Override
    public void deleteOrderDetails(Long id) {
        orderDetailsRepository.deleteById(id);
    }
}
