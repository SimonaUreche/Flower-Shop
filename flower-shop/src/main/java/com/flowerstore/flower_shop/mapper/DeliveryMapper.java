package com.flowerstore.flower_shop.mapper;

import com.flowerstore.flower_shop.dto.DeliveryDTO;
import com.flowerstore.flower_shop.model.Delivery;
import com.flowerstore.flower_shop.model.Order;
import com.flowerstore.flower_shop.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class DeliveryMapper {

    public static DeliveryDTO toDto(Delivery delivery) {
        return DeliveryDTO.builder()
                .id(delivery.getId())
                .orderId(delivery.getOrder().getId())
                .delivererId(delivery.getDeliverer().getId())
                .deliveryStatus(delivery.getDeliveryStatus())
                .build();
    }

    public static List<DeliveryDTO> toDtoList(List<Delivery> deliveries) {
        return deliveries.stream().map(DeliveryMapper::toDto).collect(Collectors.toList());
    }

    public static Delivery toEntity(DeliveryDTO dto, Order order, User deliverer) {
        return Delivery.builder()
                .id(dto.getId())
                .order(order)
                .deliverer(deliverer)
                .deliveryStatus(dto.getDeliveryStatus())
                .build();
    }
}
