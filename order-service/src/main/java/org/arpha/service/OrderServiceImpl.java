package org.arpha.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.order.request.CreateOrderRequest;
import org.arpha.dto.order.response.OrderDetailsResponse;
import org.arpha.exception.CreateOrderException;
import org.arpha.mapper.OrderMapper;
import org.arpha.repository.OrderRepository;
import org.arpha.utils.Boxed;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductService productService;

    @Override
    public OrderDetailsResponse createOrder(CreateOrderRequest createOrderRequest) {
        return Boxed
                .of(createOrderRequest)
                .doWith(createOrderRequest1 -> productService.updateQuantity(createOrderRequest1.getOrderItems()))
                .mapToBoxed(orderMapper::toOrder)
                .mapToBoxed(orderRepository::save)
                .mapToBoxed(orderMapper::toOrderDetailsResponse)
                .orElseThrow(() -> new CreateOrderException("Not enough quantity of item in store"));
    }



}
