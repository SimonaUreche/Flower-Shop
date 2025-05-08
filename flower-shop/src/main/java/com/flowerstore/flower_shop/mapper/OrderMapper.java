package com.flowerstore.flower_shop.mapper;

import com.flowerstore.flower_shop.dto.OrderDTO;
import com.flowerstore.flower_shop.dto.OrderDetailsDTO;
import com.flowerstore.flower_shop.model.Order;
import com.flowerstore.flower_shop.model.OrderDetails;
import com.flowerstore.flower_shop.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser() != null ? order.getUser().getId() : null);
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus());

        dto.setOrderDetails(
                order.getOrderDetails().stream()
                        .map(detail -> new OrderDetailsDTO(
                                null,
                                detail.getProduct().getId(),
                                detail.getProduct().getName(),
                                detail.getQuantity(),
                                detail.getPriceAtOrder(),
                                detail.getSubtotal()
                        ))
                        .collect(Collectors.toList())
        );

        return dto;
    }

}
