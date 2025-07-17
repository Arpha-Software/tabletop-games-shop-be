package org.arpha.dto.order.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class OrderAnalyticsDto {

    private long id;
    private OffsetDateTime createdAt;
    private BigDecimal totalCost;
    private List<OrderItemAnalyticsDto> orderedItems;

    @Data
    @Builder
    public static class OrderItemAnalyticsDto {
        private long productId;
        private String productName;
        private int quantity;
        private BigDecimal price;
    }
}