package org.arpha.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.product.request.CreateReviewRequest;
import org.arpha.dto.product.response.ReviewResponse;
import org.arpha.dto.user.response.UserResponse;
import org.arpha.service.ReviewService;
import org.arpha.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userServiceClient; // Inject the Feign client

    @PostMapping("/reviews")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReviewResponse> createReview(
            @Valid @RequestBody CreateReviewRequest request,
            Authentication authentication) { // Inject the standard Authentication object

        // 1. Get the user's email from the standard security principal
        String userEmail = authentication.getName();

        // 2. Call the user-service to get details
        UserResponse currentUser = userServiceClient.findUserByEmail(userEmail);

        // 3. Pass primitive data to the service layer
        ReviewResponse response = reviewService.createReview(
                request,
                currentUser.getId(),
                currentUser.getFirstName() // Use a display name
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<Page<ReviewResponse>> getReviewsForProduct(
            @PathVariable Long productId, Pageable pageable) {
        Page<ReviewResponse> reviews = reviewService.getReviewsForProduct(productId, pageable);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/reviews/{reviewId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            Authentication authentication) { // Inject the standard Authentication object

        String userEmail = authentication.getName();
        UserResponse currentUser = userServiceClient.findUserByEmail(userEmail);

        // Pass primitive data for the authorization check
        reviewService.deleteReview(
                reviewId,
                currentUser.getId(),
                currentUser.getRole()
        );
        return ResponseEntity.noContent().build();
    }
}