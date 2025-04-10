package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.model.Review;
import com.flowerstore.flower_shop.service.IReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final IReviewService iReviewService;

    public ReviewController(IReviewService iReviewService) {
        this.iReviewService = iReviewService;
    }

    @GetMapping
    public ResponseEntity findAllReviews() {
        return ResponseEntity.status(HttpStatus.OK).body(iReviewService.getAllReviews());
    }

    @PostMapping
    public ResponseEntity saveNewReview(@RequestBody Review review) {
        return ResponseEntity.status(HttpStatus.OK).body(iReviewService.addReview(review));
    }

    @GetMapping("/{id}")
    public ResponseEntity getReview(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iReviewService.getReviewsById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}