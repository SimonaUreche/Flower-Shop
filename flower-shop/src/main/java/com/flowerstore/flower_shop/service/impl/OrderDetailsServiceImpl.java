package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.OrderDetails;
import com.flowerstore.flower_shop.repository.OrderDetailsRepository;
import com.flowerstore.flower_shop.service.IOrderDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
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
        return orderDetailsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("OrderDetails with id " + id + " not found"));
    }

    @Override
    public OrderDetails updateOrderDetails(OrderDetails orderDetails) {
        if (!orderDetailsRepository.existsById(orderDetails.getId())) {
            throw new NoSuchElementException("OrderDetails not found for update");
        }
        return orderDetailsRepository.save(orderDetails);
    }

    @Override
    public void deleteOrderDetails(Long id) {
        orderDetailsRepository.deleteById(id);
    }
}
