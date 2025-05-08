package com.flowerstore.flower_shop.mapper;

import com.flowerstore.flower_shop.dto.CategoryDTO;
import com.flowerstore.flower_shop.model.Category;

public class CategoryMapper {

    public static CategoryDTO toDto(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .build();
    }
}
