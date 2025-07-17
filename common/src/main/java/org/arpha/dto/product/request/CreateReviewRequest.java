package org.arpha.dto.product.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateReviewRequest {
    @NotNull
    private Long productId;

    @Min(1)
    @Max(5)
    private int rating;

    @Size(max = 2000)
    private String comment;
}