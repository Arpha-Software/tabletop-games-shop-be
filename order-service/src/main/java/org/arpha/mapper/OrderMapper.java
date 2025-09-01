package org.arpha.mapper;

import org.arpha.dto.order.Document;
import org.arpha.dto.order.request.CreateOrderRequest;
import org.arpha.dto.order.response.CreateConsignmentDocumentResponse;
import org.arpha.dto.order.response.OrderAnalyticsDto;
import org.arpha.dto.order.response.OrderDetailsResponse;
import org.arpha.dto.order.response.OrderInfoResponse;
import org.arpha.dto.order.response.OrderStatusHistoryResponse;
import org.arpha.entity.Order;
import org.arpha.entity.OrderItem;
import org.arpha.entity.OrderStatusHistory;
import org.arpha.mapper.helper.OrderMapperHelper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", uses = {OrderMapperHelper.class}, unmappedTargetPolicy = IGNORE)
public interface OrderMapper {

    @Mapping(target = "user", source = "createOrderRequest", qualifiedByName = "getUser")
    @Mapping(target = "customerDetails.firstName", source = "customerDetails.firstName")
    @Mapping(target = "customerDetails.middleName", source = "customerDetails.middleName")
    @Mapping(target = "customerDetails.lastName", source = "customerDetails.lastName")
    @Mapping(target = "customerDetails.phoneNumber", source = "customerDetails.phoneNumber")
    @Mapping(target = "customerDetails.email", source = "customerDetails.email")
    @Mapping(target = "deliveryDetails.deliveryType", source = "deliveryDetails.deliveryType")
    @Mapping(target = "deliveryDetails.deliveryAddress.city", source = "deliveryDetails.city")
    @Mapping(target = "deliveryDetails.deliveryAddress.cityCode", source = "deliveryDetails.cityCode")
    @Mapping(target = "deliveryDetails.deliveryAddress.street", source = "deliveryDetails.street")
    @Mapping(target = "deliveryDetails.deliveryAddress.streetCode", source = "deliveryDetails.streetCode")
    @Mapping(target = "deliveryDetails.deliveryAddress.houseNumber", source = "deliveryDetails.houseNumber")
    @Mapping(target = "deliveryDetails.deliveryAddress.flatNumber", source = "deliveryDetails.flatNumber")
    @Mapping(target = "deliveryDetails.deliveryAddress.department", source = "deliveryDetails.department")
    @Mapping(target = "deliveryDetails.deliveryAddress.departmentCode", source = "deliveryDetails.departmentCode")
    @Mapping(target = "orderStatus", constant = "NEW")
    @Mapping(target = "orderedItems", source = "orderedItems", qualifiedByName = "toOrderedItems")
    Order toOrder(CreateOrderRequest createOrderRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "deliveryDetails.deliveryType", source = "deliveryDetails.deliveryType")
    @Mapping(target = "deliveryDetails.city", source = "deliveryDetails.deliveryAddress.city")
    @Mapping(target = "deliveryDetails.street", source = "deliveryDetails.deliveryAddress.street")
    @Mapping(target = "deliveryDetails.houseNumber", source = "deliveryDetails.deliveryAddress.houseNumber")
    @Mapping(target = "deliveryDetails.flatNumber", source = "deliveryDetails.deliveryAddress.flatNumber")
    @Mapping(target = "deliveryDetails.department", source = "deliveryDetails.deliveryAddress.department")
    @Mapping(target = "deliveryDetails.expectedDeliveryDate", source = "deliveryDetails.expectedDeliveryDate")
    @Mapping(target = "deliveryDetails.docNumber", source = "deliveryDetails.docNumber")
    @Mapping(target = "deliveryDetails.deliveryPrice", source = "deliveryDetails.deliveryPrice")
    @Mapping(target = "customerDetails.firstName", source = "customerDetails.firstName")
    @Mapping(target = "customerDetails.middleName", source = "customerDetails.middleName")
    @Mapping(target = "customerDetails.lastName", source = "customerDetails.lastName")
    @Mapping(target = "customerDetails.phoneNumber", source = "customerDetails.phoneNumber")
    @Mapping(target = "customerDetails.email", source = "customerDetails.email")
    @Mapping(target = "orderStatus", source = "orderStatus")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "orderedItems", source = "orderedItems", qualifiedByName = "toItemDetails")
    @Mapping(target = "orderPriceSummary", source = "order", qualifiedByName = "getOrderPriceSummary")
    @Mapping(target = "orderQuantity", source = "order", qualifiedByName = "getOrderItemsQuantity")
    OrderInfoResponse toOrderInfoResponse(Order order);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "order", qualifiedByName = "toUserId")
    @Mapping(target = "city", source = "deliveryDetails.deliveryAddress.city")
    @Mapping(target = "street", source = "deliveryDetails.deliveryAddress.street")
    @Mapping(target = "houseNumber", source = "deliveryDetails.deliveryAddress.houseNumber")
    @Mapping(target = "flatNumber", source = "deliveryDetails.deliveryAddress.flatNumber")
    @Mapping(target = "department", source = "deliveryDetails.deliveryAddress.department")
    @Mapping(target = "expectedDeliveryDate", source = "deliveryDetails.expectedDeliveryDate")
    @Mapping(target = "orderStatus", source = "orderStatus")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "orderPriceSummary", source = "order", qualifiedByName = "getOrderPriceSummary")
    @Mapping(target = "orderQuantity", source = "order", qualifiedByName = "getOrderItemsQuantity")
    OrderDetailsResponse toOrderDetailsResponse(Order order);

    @Mapping(target = "order.deliveryDetails.deliveryPrice", source = "document.costOnSite", qualifiedByName = "toDeliveryPrice")
    @Mapping(target = "order.deliveryDetails.expectedDeliveryDate", source = "document.estimatedDeliveryDate", qualifiedByName = "toDate")
    @Mapping(target = "order.deliveryDetails.docNumber", source = "document.intDocNumber")
    @Mapping(target = "order.deliveryDetails.documentRef", source = "document.ref")
    @Mapping(target = "order.orderStatus", constant = "CREATED_CONSIGNMENT")//Clarify this
    void addDocumentDataToOrder(@MappingTarget Order order, Document document);

    @Mapping(target = "totalCost", expression = "java(order.getTotalCost())")
    OrderAnalyticsDto toOrderAnalyticsDto(Order order);

    List<OrderAnalyticsDto> toOrderAnalyticsDtoList(List<Order> orders);

    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "productId", source = "product.id")
    OrderAnalyticsDto.OrderItemAnalyticsDto toOrderItemAnalyticsDto(OrderItem orderItem);

    List<OrderStatusHistoryResponse> toOrderStatusHistoryResponseList(List<OrderStatusHistory> history);

    @AfterMapping
    default void setOrderForIter(@MappingTarget Order order) {
        order.getOrderedItems().forEach(orderItem -> orderItem.setOrder(order));
    }

}
