package org.arpha.dto.user.response.analytics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@Schema(description = "Detailed information about a single order for analytics")
public class OrderAnalyticsInfo {

    @Schema(description = "The unique identifier of the order")
    private long orderId;

    @Schema(description = "The timestamp when the order was created")
    private OffsetDateTime orderDate;

    @Schema(description = "The total sum of the order")
    private BigDecimal totalSum;

    @Schema(description = "List of products included in the order")
    private List<ProductAnalyticsInfo> products;
}