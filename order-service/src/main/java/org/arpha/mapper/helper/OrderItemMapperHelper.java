package org.arpha.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.enums.TargetType;
import org.arpha.dto.order.request.CreateOrderItem;
import org.arpha.entity.Product;
import org.arpha.exception.CreateOrderException;
import org.arpha.repository.ProductRepository;
import org.arpha.service.MediaService;
import org.arpha.utils.Boxed;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderItemMapperHelper {

    private final ProductRepository productRepository;
    private final MediaService mediaService;

    @Named("toOrderItemEntity")
    public Product toOrderItemEntity(CreateOrderItem createOrderItem) {
        return Boxed
                .of(createOrderItem)
                .mapToBoxed(CreateOrderItem::getProductId)
                .flatOpt(productRepository::findById)
                .doWith(product -> validateQuantity(createOrderItem, product))
                .doWith(product -> product.setQuantity(product.getQuantity() - createOrderItem.getQuantity()))
                .orElseThrow(() -> new CreateOrderException("Product with %s id doesn't exist!".formatted(createOrderItem.getProductId())));
    }

    @Named("toMainImg")
    public String toMainImg(Product product) {
        return mediaService.getFileLink(product.getId(), TargetType.PRODUCT_MAIN_IMG);
    }

    private void validateQuantity(CreateOrderItem createOrderItem, Product product) {
        if (product.getQuantity() < createOrderItem.getQuantity()) {
            throw new CreateOrderException("Product with %s id doesn't have enough quantity to create order!"
                    .formatted(createOrderItem.getProductId()));
        }
    }

}
