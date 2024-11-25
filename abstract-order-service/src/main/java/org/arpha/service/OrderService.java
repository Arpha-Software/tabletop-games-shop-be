package org.arpha.service;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.order.request.CreateOrderRequest;
import org.arpha.dto.order.response.OrderDetailsResponse;
import org.arpha.dto.order.response.OrderInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderInfoResponse createOrder(CreateOrderRequest createOrderRequest);
    Page<OrderDetailsResponse> getUsersOrders(long userId, Predicate predicate, Pageable pageable);
    Page<OrderDetailsResponse> getsOrders(Predicate predicate, Pageable pageable);
    OrderInfoResponse getById(long orderId);
    void cancelOrder(long orderId);

}
