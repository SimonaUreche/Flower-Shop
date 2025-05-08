package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.dto.ReviewDTO;
import com.flowerstore.flower_shop.mapper.ReviewMapper;
import com.flowerstore.flower_shop.model.Product;
import com.flowerstore.flower_shop.model.Review;
import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.service.IProductService;
import com.flowerstore.flower_shop.service.IReviewService;
import com.flowerstore.flower_shop.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final IReviewService reviewService;
    private final IUserService userService;
    private final IProductService productService;

    public ReviewController(IReviewService reviewService, IUserService userService, IProductService productService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews().stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(ReviewMapper.toDTO(reviewService.getReviewsById(id)));
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO dto) {
        User user = userService.getUserById(dto.getUserId());
        Product product = productService.getProductById(dto.getProductId());
        Review saved = reviewService.addReview(ReviewMapper.toEntity(dto, user, product));
        return ResponseEntity.ok(ReviewMapper.toDTO(saved));
    }

}
