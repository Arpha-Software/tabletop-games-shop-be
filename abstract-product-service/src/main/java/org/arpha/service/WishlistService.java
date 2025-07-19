package org.arpha.service;

import org.arpha.dto.product.response.WishlistResponse;

public interface WishlistService {
    WishlistResponse getWishlistByUserId(String email);
    WishlistResponse addProductToWishlist(String email, Long productId);
    void removeProductFromWishlist(String email, Long productId);
    WishlistResponse getWishlistByShareableLink(String shareableLink);
}