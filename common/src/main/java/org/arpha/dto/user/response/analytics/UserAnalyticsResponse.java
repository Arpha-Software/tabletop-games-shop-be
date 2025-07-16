package org.arpha.dto.user.response.analytics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.arpha.dto.user.UserDetails;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@Schema(description = "Full analytics data for a specific user")
public class UserAnalyticsResponse {

    @Schema(description = "General details of the user")
    private UserDetails userDetails;

    @Schema(description = "Total number of orders placed by the user")
    private int totalOrders;

    @Schema(description = "Timestamp of the user's first order")
    private OffsetDateTime firstOrderDate;

    @Schema(description = "Timestamp of the user's most recent order")
    private OffsetDateTime lastOrderDate;

    @Schema(description = "A list of all orders with their details")
    private List<OrderAnalyticsInfo> orders;
}