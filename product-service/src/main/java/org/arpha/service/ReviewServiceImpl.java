package org.arpha.service;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.product.request.CreateReviewRequest;
import org.arpha.dto.product.response.RatingStats;
import org.arpha.dto.product.response.ReviewResponse;
import org.arpha.entity.Product;
import org.arpha.entity.Review;
import org.arpha.exception.ProductNotFoundException;
import org.arpha.mapper.ReviewMapper;
import org.arpha.repository.ProductRepository;
import org.arpha.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request, Long userId, String username) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Review review = reviewMapper.toReview(request);
        review.setProduct(product);
        review.setUserId(userId);
        review.setUsername(username); // Use the provided username

        Review savedReview = reviewRepository.save(review);
        updateProductRating(product);

        return reviewMapper.toReviewResponse(savedReview);
    }

    @Override
    @Transactional 
    public Page<ReviewResponse> getReviewsForProduct(Long productId, Pageable pageable) {
        return reviewRepository.findByProductId(productId, pageable)
                .map(reviewMapper::toReviewResponse);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId, Long userId, String userRole) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUserId().equals(userId) && !"ROLE_ADMIN".equals(userRole)) {
            throw new AccessDeniedException("You do not have permission to delete this review.");
        }

        Product product = review.getProduct();
        reviewRepository.delete(review);
        updateProductRating(product);
    }

    private void updateProductRating(Product product) {
        RatingStats ratingStats = reviewRepository.getRatingStatsByProductId(product.getId());
        Double averageRating = ratingStats.average();
        long reviewCount = ratingStats.count();

        product.setAverageRating(averageRating != null ? averageRating : 0.0);
        product.setReviewCount((int) reviewCount);
        productRepository.save(product);
    }
}
