package org.arpha.service;

import org.arpha.dto.order.request.CreateOrderRequest;
import org.arpha.dto.order.response.OrderDetailsResponse;

public interface OrderService {

    OrderDetailsResponse createOrder(CreateOrderRequest createOrderRequest);

}
