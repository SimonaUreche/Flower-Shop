package com.flowerstore.flower_shop.repository;

import com.flowerstore.flower_shop.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;

@Repository
public class OrderRepository {
    private final List<Order> orders =  new ArrayList<>();
    private Long nextId = 1L;

    //CREATE
    public Order save(Order order) {
        if(order.getId()==null) {
            order.setId(nextId++);
        }
        orders.add(order);
        return order;
    }

    //READ
    public List<Order> findAll() {
        return orders;
    }

    public Order findById(Long id) {
        return orders.stream().filter(order -> order.getId().equals(id)).findFirst().orElse(null);
    }

    //UPDATE
    public Order updateOrderStatus(Order order) { //update se face doar pe status la Order
        Order oldOrder = findById(order.getId());

        if(oldOrder != null) {
            oldOrder.setStatus(order.getStatus());
            return oldOrder;
        }
        return null;
    }

    //DELETE
    public void deleteById(Long id) {
        orders.removeIf(order -> order.getId().equals(id));
    }

    public void saveAll(List<Order> orders) {

    }
}
