package com.flowerstore.flower_shop.repository;

import com.flowerstore.flower_shop.model.Delivery;

import java.util.ArrayList;
import java.util.List;

public class DeliveryRepository {
    private final List<Delivery> deliveries = new ArrayList<>();
    private Long nextId = 1L;

    public Delivery save(Delivery delivery) {
        if(delivery.getId() == null) {
            delivery.setId(nextId++);
        }
        deliveries.add(delivery);
        return delivery;
    }

    public List<Delivery> findAll() {
        return deliveries;
    }

    public Delivery findById(Long id) {
        return deliveries.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public Delivery updateDelivery(Delivery product) {
        Delivery oldDelivery = findById(product.getId());

        if(oldDelivery != null){
            oldDelivery.setDeliverer(product.getDeliverer());
            oldDelivery.setOrder(product.getOrder());
            oldDelivery.setDeliveryStatus(product.getDeliveryStatus());
            return oldDelivery;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return deliveries.removeIf(product -> product.getId().equals(id));
    }

}
