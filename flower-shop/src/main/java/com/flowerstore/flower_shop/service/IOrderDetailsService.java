package com.flowerstore.flower_shop.service;

import com.flowerstore.flower_shop.model.OrderDetails;

import java.util.List;

public interface IOrderDetailsService {
    OrderDetails addOrderDetails(OrderDetails orderDetails);
    List<OrderDetails> getAllOrderDetails();
    OrderDetails getOrderDetailsById(Long id);
    OrderDetails updateOrderDetails(OrderDetails orderDetails);
    void deleteOrderDetails(Long id);
}
