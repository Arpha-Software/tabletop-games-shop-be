package org.arpha.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.order.ItemDetails;
import org.arpha.dto.order.request.CreateOrderItem;
import org.arpha.entity.OrderItem;
import org.arpha.entity.User;
import org.arpha.mapper.OrderItemMapper;
import org.arpha.security.UserDetailsAdapter;
import org.mapstruct.Named;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapperHelper {

    private final OrderItemMapper orderItemMapper;

    @Named("getUser")
    public User getUser(Object plug) {
        return  ((UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).user();
    }

    @Named("toDeliveryPrice")
    public BigDecimal toDeliveryPrice(String deliveryPrice) {
        return new BigDecimal(deliveryPrice);
    }

    @Named("toDate")
    public LocalDate toLocalDate(String date) {
        return LocalDate.parse(date);
    }

    @Named("toOrderedItems")
    public List<OrderItem> toOrderedItems(List<CreateOrderItem> createOrderItems) {
        return orderItemMapper.toOrderItems(createOrderItems);
    }

    @Named("toItemDetails")
    public List<ItemDetails> toItemDetails(List<OrderItem> createOrderItems) {
        return orderItemMapper.toItemDetails(createOrderItems);
    }

}
