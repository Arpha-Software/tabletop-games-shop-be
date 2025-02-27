package org.arpha.mapper;

import org.arpha.dto.order.ItemDetails;
import org.arpha.dto.order.request.CreateOrderItem;
import org.arpha.entity.OrderItem;
import org.arpha.mapper.helper.OrderItemMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", uses = {OrderItemMapperHelper.class}, unmappedTargetPolicy = IGNORE)
public interface OrderItemMapper {
    @Mapping(target = "product", source = "createOrderItem", qualifiedByName = "toOrderItemEntity")
    @Mapping(target = "quantity", source = "createOrderItem.quantity")
    OrderItem toOrderItem(CreateOrderItem createOrderItem);

    List<OrderItem> toOrderItems(List<CreateOrderItem> createOrderItems);

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "mainImg", source = "product", qualifiedByName = "toMainImg")
    ItemDetails toItemDetails(OrderItem orderItem);

    List<ItemDetails> toItemDetails(List<OrderItem> orderItems);
}
