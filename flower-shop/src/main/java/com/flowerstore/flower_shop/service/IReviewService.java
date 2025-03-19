package com.flowerstore.flower_shop.service;

import com.flowerstore.flower_shop.model.Review;

import java.util.List;

public interface IReviewService {
    Review addReview(Review review);
    List<Review> getAllReviews();
    Review getReviewsById(Long id);
}
