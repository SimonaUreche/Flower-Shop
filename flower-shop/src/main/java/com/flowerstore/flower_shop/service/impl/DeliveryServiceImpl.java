package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.Delivery;
import com.flowerstore.flower_shop.repository.DeliveryRepository;
import com.flowerstore.flower_shop.service.IDeliveryService;

import java.util.List;
import java.util.NoSuchElementException;

public class DeliveryServiceImpl implements IDeliveryService {
    private final DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(DeliveryRepository repository) {
        this.deliveryRepository = repository;
    }
    @Override
    public Delivery addDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    @Override
    public Delivery getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id);
        if(delivery != null) {
            return delivery;
        }
        throw new NoSuchElementException("Delivery with id" +  id + " does not exist");
    }

    @Override
    public Delivery updateDelivery(Delivery delivery) {
        return deliveryRepository.updateDelivery(delivery);
    }

    @Override
    public void deleteDelivery(Long id) {
        deliveryRepository.deleteById(id);
    }
}
