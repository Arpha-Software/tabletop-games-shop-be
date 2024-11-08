package org.arpha.mapper;

import jdk.jfr.Name;
import org.arpha.dto.order.request.CreateOrderItem;
import org.arpha.entity.OrderItem;
import org.arpha.mapper.helper.OrderItemMapperHelper;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapperHelper.class})
public interface OrderItemMapper {

    @Named("toOrderItem")
    @Mapping(target = "product", source = "createOrderItem", qualifiedByName = "toOrderItemEntity")
    @Mapping(target = "quantity", source = "createOrderItem.quantity")
    OrderItem toOrderItem(CreateOrderItem createOrderItem);

    @Name("toOrderItems")
    @IterableMapping(qualifiedByName = "toOrderItem")
    List<OrderItem> toOrderItems(List<CreateOrderItem> createOrderItems);
}
