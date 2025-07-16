package org.arpha.dto.user.response.analytics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Information about a product within an order")
public class ProductAnalyticsInfo {

    @Schema(description = "The name of the product")
    private String name;

    @Schema(description = "The quantity of this product ordered")
    private int quantity;

    @Schema(description = "The price of a single unit of the product")
    private BigDecimal price;
}