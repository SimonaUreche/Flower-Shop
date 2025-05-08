package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.Delivery;
import com.flowerstore.flower_shop.repository.DeliveryRepository;
import com.flowerstore.flower_shop.service.IDeliveryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
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
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Delivery with id " + id + " not found"));
    }

    @Override
    public Delivery updateDelivery(Delivery delivery) {
        if (!deliveryRepository.existsById(delivery.getId())) {
            throw new NoSuchElementException("Delivery not found for update");
        }
        return deliveryRepository.save(delivery);
    }

    @Override
    public void deleteDelivery(Long id) {
        deliveryRepository.deleteById(id);
    }
}
