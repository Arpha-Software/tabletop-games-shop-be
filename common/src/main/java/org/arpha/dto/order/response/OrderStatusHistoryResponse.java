package org.arpha.dto.order.response;

import lombok.Data;
import org.arpha.dto.order.enums.OrderStatus;

import java.time.OffsetDateTime;

@Data
public class OrderStatusHistoryResponse {
    private OrderStatus status;
    private OffsetDateTime changedAt;
    private String notes;
}