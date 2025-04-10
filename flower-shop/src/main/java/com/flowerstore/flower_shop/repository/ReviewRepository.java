package com.flowerstore.flower_shop.repository;

import com.flowerstore.flower_shop.model.Review;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepository {
    private final List<Review> reviews = new ArrayList<>();
    private Long nextId = 1L;

    public Review save(Review review) {
        if(review.getId() == null) {
            review.setId(nextId++);
        }
        reviews.add(review);
        return review;
    }

    public List<Review> findAll() {
        return reviews;
    }

    public Review findById(Long id) {
        return reviews.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }
    //un review nu poate fi modificat si nici sters

}
