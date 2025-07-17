package org.arpha.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendedProductResponse {
    private ProductResponse product;
    private RecommendationReason reason;
}