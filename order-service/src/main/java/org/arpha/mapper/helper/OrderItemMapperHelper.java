package org.arpha.mapper.helper;

import jdk.jfr.Name;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.order.request.CreateOrderItem;
import org.arpha.entity.OrderItem;
import org.arpha.entity.Product;
import org.arpha.exception.ProductNotFoundException;
import org.arpha.repository.ProductRepository;
import org.arpha.utils.Boxed;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemMapperHelper {

    private final ProductRepository productRepository;

    @Name("toOrderItemEntity")
    public Product toOrderItemEntity(CreateOrderItem createOrderItem) {
        return Boxed
                .of(createOrderItem)
                .mapToBoxed(CreateOrderItem::getProductId)
                .flatOpt(productRepository::findById)
                .orElseThrow(() -> new ProductNotFoundException("Product with %s id doesn't exist!".formatted(createOrderItem.getProductId())));
    }

}
