package com.flowerstore.flower_shop.service;

import com.flowerstore.flower_shop.model.Order;

import java.util.List;

public interface IOrderService {
    Order addOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order updateOrder(Order order);
    void deleteOrder(Long id);
}
