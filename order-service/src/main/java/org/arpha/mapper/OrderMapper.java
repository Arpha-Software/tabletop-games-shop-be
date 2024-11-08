package org.arpha.mapper;

import org.arpha.dto.order.request.CreateOrderRequest;
import org.arpha.dto.order.response.OrderDetailsResponse;
import org.arpha.entity.Order;
import org.arpha.mapper.helper.OrderMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderMapperHelper.class, OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "user", source = "createOrderRequest", qualifiedByName = "getUser")
    @Mapping(target = "customerDetails.firstName", source = "customerDetails.firstName")
    @Mapping(target = "customerDetails.middleName", source = "customerDetails.middleName")
    @Mapping(target = "customerDetails.lastName", source = "customerDetails.lastName")
    @Mapping(target = "customerDetails.phoneNumber", source = "customerDetails.phoneNumber")
    @Mapping(target = "customerDetails.email", source = "customerDetails.email")
    @Mapping(target = "deliveryDetails.deliveryType", source = "deliveryDetails.deliveryType")
    @Mapping(target = "deliveryDetails.city", source = "deliveryDetails.city")
    @Mapping(target = "deliveryDetails.street", source = "deliveryDetails.street")
    @Mapping(target = "deliveryDetails.houseNumber", source = "deliveryDetails.houseNumber")
    @Mapping(target = "deliveryDetails.flatNumber", source = "deliveryDetails.flatNumber")
    @Mapping(target = "orderItems", source = "createOrderItems", qualifiedByName = "toOrderItems")
    Order toOrder(CreateOrderRequest createOrderRequest);


    @Mapping(target = "deliveryDetails.deliveryType", source = "deliveryDetails.deliveryType")
    @Mapping(target = "deliveryDetails.city", source = "deliveryDetails.city")
    @Mapping(target = "deliveryDetails.street", source = "deliveryDetails.street")
    @Mapping(target = "deliveryDetails.houseNumber", source = "deliveryDetails.houseNumber")
    @Mapping(target = "deliveryDetails.flatNumber", source = "deliveryDetails.flatNumber")
    @Mapping(target = "customerDetails.firstName", source = "customerDetails.firstName")
    @Mapping(target = "customerDetails.firstName", source = "customerDetails.firstName")
    @Mapping(target = "customerDetails.middleName", source = "customerDetails.middleName")
    @Mapping(target = "customerDetails.lastName", source = "customerDetails.lastName")
    @Mapping(target = "customerDetails.phoneNumber", source = "customerDetails.phoneNumber")
    @Mapping(target = "customerDetails.email", source = "customerDetails.email")
    OrderDetailsResponse toOrderDetailsResponse(Order order);
}
