package org.arpha.repository;

import org.arpha.dto.product.response.RatingStats;
import org.arpha.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByProductId(Long productId, Pageable pageable);

    @Query("SELECT new org.arpha.dto.product.response.RatingStats(AVG(r.rating), COUNT(r)) FROM Review r WHERE r.product.id = :productId")
    RatingStats getRatingStatsByProductId(Long productId);

    long countByProductId(Long productId);

    void deleteAllByProductId(Long productId);
}