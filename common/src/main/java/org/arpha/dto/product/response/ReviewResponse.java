package org.arpha.dto.product.response;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class ReviewResponse {
    private long id;
    private int rating;
    private String comment;
    private String username;
    private OffsetDateTime createdAt;
}