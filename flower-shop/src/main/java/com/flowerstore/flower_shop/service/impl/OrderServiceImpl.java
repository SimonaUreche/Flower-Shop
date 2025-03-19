package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.Order;
import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.repository.OrderRepository;
import com.flowerstore.flower_shop.service.IOrderService;
import com.flowerstore.flower_shop.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;

    public  OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id);
        if(order != null){
            return order;
        }
        throw new NoSuchElementException("Order with id " + id + " not found");
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.updateOrderStatus(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
