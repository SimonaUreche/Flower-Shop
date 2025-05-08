package com.flowerstore.flower_shop.mapper;

import com.flowerstore.flower_shop.dto.OrderDetailsDTO;
import com.flowerstore.flower_shop.model.Order;
import com.flowerstore.flower_shop.model.OrderDetails;
import com.flowerstore.flower_shop.model.Product;

public class OrderDetailsMapper {
    public static OrderDetailsDTO toDTO(OrderDetails details) {
        OrderDetailsDTO dto = new OrderDetailsDTO();
        dto.setId(details.getId());
        dto.setProductName(details.getProduct().getName());
        dto.setQuantity(details.getQuantity());
        dto.setPrice(details.getPriceAtOrder());
        dto.setSubtotal(details.getSubtotal());
        return dto;
    }

    public static OrderDetails toEntity(OrderDetailsDTO dto, Product product, Order order) {
        return OrderDetails.builder()
                .id(dto.getId())
                .product(product)
                .order(order)
                .quantity(dto.getQuantity())
                .priceAtOrder(dto.getPrice())
                .subtotal(dto.getSubtotal())
                .build();
    }
}
