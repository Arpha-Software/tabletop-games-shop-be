package org.arpha.mapper;

import org.arpha.dto.order.Document;
import org.arpha.dto.order.request.CreateOrderRequest;
import org.arpha.dto.order.response.CreateConsignmentDocumentResponse;
import org.arpha.dto.order.response.OrderDetailsResponse;
import org.arpha.dto.order.response.OrderInfoResponse;
import org.arpha.entity.Order;
import org.arpha.mapper.helper.OrderMapperHelper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {OrderMapperHelper.class, OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "user", source = "createOrderRequest", qualifiedByName = "getUser")
    @Mapping(target = "customerDetails.firstName", source = "customerDetails.firstName")
    @Mapping(target = "customerDetails.middleName", source = "customerDetails.middleName")
    @Mapping(target = "customerDetails.lastName", source = "customerDetails.lastName")
    @Mapping(target = "customerDetails.phoneNumber", source = "customerDetails.phoneNumber")
    @Mapping(target = "customerDetails.email", source = "customerDetails.email")
    @Mapping(target = "deliveryDetails.deliveryType", source = "deliveryDetails.deliveryType")
    @Mapping(target = "deliveryDetails.deliveryAddress.city", source = "deliveryDetails.city")
    @Mapping(target = "deliveryDetails.deliveryAddress.street", source = "deliveryDetails.street")
    @Mapping(target = "deliveryDetails.deliveryAddress.houseNumber", source = "deliveryDetails.houseNumber")
    @Mapping(target = "deliveryDetails.deliveryAddress.flatNumber", source = "deliveryDetails.flatNumber")
    @Mapping(target = "deliveryDetails.deliveryAddress.department", source = "deliveryDetails.department")
    @Mapping(target = "orderStatus", constant = "NEW")
    @Mapping(target = "orderedItems", source = "orderedItems")
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
    @Mapping(target = "orderedItems", source = "orderedItems")
    OrderInfoResponse toOrderInfoResponse(Order order);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "city", source = "deliveryDetails.deliveryAddress.city")
    @Mapping(target = "street", source = "deliveryDetails.deliveryAddress.street")
    @Mapping(target = "houseNumber", source = "deliveryDetails.deliveryAddress.houseNumber")
    @Mapping(target = "flatNumber", source = "deliveryDetails.deliveryAddress.flatNumber")
    @Mapping(target = "department", source = "deliveryDetails.deliveryAddress.department")
    @Mapping(target = "expectedDeliveryDate", source = "deliveryDetails.expectedDeliveryDate")
    @Mapping(target = "orderStatus", source = "orderStatus")
    @Mapping(target = "createdAt", source = "createdAt")
    OrderDetailsResponse toOrderDetailsResponse(Order order);

    @Mapping(target = "order.deliveryDetails.deliveryPrice", source = "document.costOnSite", qualifiedByName = "toDeliveryPrice")
    @Mapping(target = "order.deliveryDetails.expectedDeliveryTime", source = "document.estimatedDeliveryDate", qualifiedByName = "toDate")
    @Mapping(target = "order.deliveryDetails.docNumber", source = "document.intDocNumber")
    @Mapping(target = "order.deliveryDetails.documentRef", source = "document.ref")
    void addDocumentDataToOrder(@MappingTarget Order order, Document document);

    @AfterMapping
    default void setOrderForIter(@MappingTarget Order order) {
        order.getOrderedItems().forEach(orderItem -> orderItem.setOrder(order));
    }

}
