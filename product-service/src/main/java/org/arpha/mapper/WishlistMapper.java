package org.arpha.mapper;

import org.arpha.dto.product.response.WishlistResponse;
import org.arpha.entity.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface WishlistMapper {

    @Mapping(target = "products", source = "products")
    WishlistResponse toWishlistResponse(Wishlist wishlist);
}