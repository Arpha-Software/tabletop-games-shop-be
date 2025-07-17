package org.arpha.dto.product.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecommendationReason {
    BASED_ON_GENRE("Because you liked products in genre '%s'"),
    BASED_ON_CATEGORY("Because you liked products in category '%s'"),
    BASED_ON_AUTHOR("Because you liked products by author '%s'");

    private final String description;
}