package org.arpha.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.order.request.CreateOrderItem;
import org.arpha.entity.Product;
import org.arpha.exception.CreateOrderException;
import org.arpha.repository.ProductRepository;
import org.arpha.utils.Boxed;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemMapperHelper {

    private final ProductRepository productRepository;

    @Named("toOrderItemEntity")
    public Product toOrderItemEntity(CreateOrderItem createOrderItem) {
        return Boxed
                .of(createOrderItem)
                .mapToBoxed(CreateOrderItem::getProductId)
                .flatOpt(productRepository::findById)
                .filter(product -> product.getQuantity() >= createOrderItem.getQuantity())
                .doWith(product -> product.setQuantity(product.getQuantity() - createOrderItem.getQuantity()))
                .orElseThrow(() -> new CreateOrderException("Product with %s id doesn't exist or quantity not enough to create order!"
                        .formatted(createOrderItem.getProductId())));
    }

}
