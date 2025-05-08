package com.flowerstore.flower_shop.mapper;

import com.flowerstore.flower_shop.dto.ProductDTO;
import com.flowerstore.flower_shop.dto.ProductCreationDTO;
import com.flowerstore.flower_shop.model.Category;
import com.flowerstore.flower_shop.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDTO toDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .image(product.getImage())
                .build();
    }

    public static List<ProductDTO> toDtoList(List<Product> productList) {
        return productList.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Product toCreationEntity(ProductCreationDTO dto, Category category) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .image(dto.getImage())
                .category(category)
                .build();
    }
}
