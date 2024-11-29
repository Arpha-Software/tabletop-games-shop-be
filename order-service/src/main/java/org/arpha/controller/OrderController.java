package org.arpha.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.order.request.CreateConsignmentDocumentRequest;
import org.arpha.dto.order.request.CreateOrderRequest;
import org.arpha.dto.order.response.OrderDetailsResponse;
import org.arpha.dto.order.response.OrderInfoResponse;
import org.arpha.entity.Order;
import org.arpha.entity.User;
import org.arpha.service.ConsignmentDocumentService;
import org.arpha.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/orders")
    public OrderInfoResponse createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createOrder(createOrderRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @authExpressions.isUserAllowed(#userId)")
    @GetMapping("/users/{userId}/orders")
    public Page<OrderDetailsResponse> getUserOrders(@PathVariable long userId, @QuerydslPredicate(root = Order.class) Predicate predicate,  @PageableDefault Pageable pageable) {
        return orderService.getUsersOrders(userId, predicate, pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/orders")
    public Page<OrderDetailsResponse> getOrders(@QuerydslPredicate(root = Order.class) Predicate predicate, @PageableDefault Pageable pageable) {
        return orderService.getsOrders(predicate, pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @authExpressions.isUserAllowed(#userId)")
    @GetMapping("/users/{userId}/orders/{orderId}")
    public OrderInfoResponse getOrderById(@PathVariable long userId, @PathVariable long orderId) {
        return orderService.getById(orderId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or @authExpressions.isUserAllowed(#userId)")
    @DeleteMapping("/users/{userId}/orders/{orderId}")
    public void cancelOrder(@PathVariable long userId, @PathVariable long orderId) {
        orderService.cancelOrder(orderId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/documents")
    public OrderInfoResponse createConsignmentDocument(@Valid @RequestBody CreateConsignmentDocumentRequest consignmentDocumentRequest) {
        return orderService.createConsignmentDocument(consignmentDocumentRequest);
    }

}
