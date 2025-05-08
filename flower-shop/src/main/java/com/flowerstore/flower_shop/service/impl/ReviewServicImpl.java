package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.Review;
import com.flowerstore.flower_shop.repository.ReviewRepository;
import com.flowerstore.flower_shop.service.IReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReviewServicImpl implements IReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServicImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review getReviewsById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Review with id " + id + " not found"));
    }
}
