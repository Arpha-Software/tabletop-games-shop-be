package org.arpha.mapper;

import org.arpha.dto.order.response.OrderAnalyticsDto;
import org.arpha.dto.user.UserDetails;
import org.arpha.dto.user.response.analytics.OrderAnalyticsInfo;
import org.arpha.dto.user.response.analytics.ProductAnalyticsInfo;
import org.arpha.dto.user.response.analytics.UserAnalyticsResponse;
import org.arpha.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Maps User entities and Order DTOs to the final UserAnalyticsResponse.
 * This mapper resides in the user-service and is completely decoupled
 * from the order-service's internal domain models.
 */
@Mapper(componentModel = "spring")
public interface AnalyticsMapper {

    /**
     * The main mapping method to create the final analytics response.
     * It orchestrates the mapping from the User and the list of order DTOs.
     *
     * @param user   The user entity for whom analytics are being generated.
     * @param orders A list of DTOs representing the user's orders, sorted by creation date descending.
     * @return A complete UserAnalyticsResponse object.
     */
    @Mapping(target = "userDetails", source = "user")
    @Mapping(target = "totalOrders", expression = "java(orders.size())")
    @Mapping(target = "firstOrderDate", expression = "java(orders.isEmpty() ? null : orders.get(orders.size() - 1).getCreatedAt())")
    @Mapping(target = "lastOrderDate", expression = "java(orders.isEmpty() ? null : orders.get(0).getCreatedAt())")
    @Mapping(target = "orders", source = "orders")
    UserAnalyticsResponse toUserAnalyticsResponse(User user, List<OrderAnalyticsDto> orders);

    /**
     * Converts a list of OrderAnalyticsDto to a list of OrderAnalyticsInfo.
     */
    List<OrderAnalyticsInfo> toOrderAnalyticsInfoList(List<OrderAnalyticsDto> orderDtos);

    /**
     * Maps a single OrderAnalyticsDto (from order-service) to an OrderAnalyticsInfo
     * which is part of the final response.
     */
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "orderDate", source = "createdAt")
    @Mapping(target = "totalSum", source = "totalCost")
    @Mapping(target = "products", source = "orderedItems")
    OrderAnalyticsInfo toOrderAnalyticsInfo(OrderAnalyticsDto orderDto);

    /**
     * Maps the inner OrderItemAnalyticsDto to the ProductAnalyticsInfo.
     */
    @Mapping(target = "name", source = "productName")
    ProductAnalyticsInfo toProductAnalyticsInfo(OrderAnalyticsDto.OrderItemAnalyticsDto itemDto);

    /**
     * Maps a User entity to a simple UserDetails DTO.
     */
    UserDetails toUserDetails(User user);
}