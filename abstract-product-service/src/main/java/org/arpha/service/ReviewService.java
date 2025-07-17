package org.arpha.service;

import org.arpha.dto.product.request.CreateReviewRequest;
import org.arpha.dto.product.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    ReviewResponse createReview(CreateReviewRequest request, Long userId, String username);
    Page<ReviewResponse> getReviewsForProduct(Long productId, Pageable pageable);
    void deleteReview(Long reviewId, Long userId, String userRole);
}