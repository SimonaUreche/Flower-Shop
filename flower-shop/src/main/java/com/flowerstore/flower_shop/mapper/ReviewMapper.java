package com.flowerstore.flower_shop.mapper;

import com.flowerstore.flower_shop.dto.ReviewDTO;
import com.flowerstore.flower_shop.model.Product;
import com.flowerstore.flower_shop.model.Review;
import com.flowerstore.flower_shop.model.User;

public class ReviewMapper {
    public static ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setUserId(review.getUser() != null ? review.getUser().getId() : null);
        dto.setProductId(review.getProduct() != null ? review.getProduct().getId() : null);
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        return dto;
    }

    public static Review toEntity(ReviewDTO dto, User user, Product product) {
        return Review.builder()
                .id(dto.getId())
                .user(user)
                .product(product)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();
    }
}
