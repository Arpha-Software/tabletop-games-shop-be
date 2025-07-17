package org.arpha.mapper;

import org.arpha.dto.product.request.CreateReviewRequest;
import org.arpha.dto.product.response.ReviewResponse;
import org.arpha.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewResponse toReviewResponse(Review review);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true) // Will be set in the service
    Review toReview(CreateReviewRequest request);
}