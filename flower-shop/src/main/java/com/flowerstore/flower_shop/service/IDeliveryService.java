package com.flowerstore.flower_shop.service;

import com.flowerstore.flower_shop.model.Delivery;

import java.util.List;

public interface IDeliveryService {
    Delivery addDelivery(Delivery delivery);
    List<Delivery> getAllDeliveries();
    Delivery getDeliveryById(Long id);
    Delivery updateDelivery(Delivery delivery);
    void deleteDelivery(Long id);
}
