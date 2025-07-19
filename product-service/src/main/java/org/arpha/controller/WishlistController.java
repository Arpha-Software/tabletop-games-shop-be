package org.arpha.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.product.response.WishlistResponse;
import org.arpha.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wishlists")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("/my-wishlist")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<WishlistResponse> getMyWishlist(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(wishlistService.getWishlistByUserId(userDetails.getUsername()));
    }

    @PostMapping("/my-wishlist/products/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<WishlistResponse> addProductToMyWishlist(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId) {
        return ResponseEntity.ok(wishlistService.addProductToWishlist(userDetails.getUsername(), productId));
    }

    @DeleteMapping("/my-wishlist/products/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> removeProductFromMyWishlist(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId) {
        wishlistService.removeProductFromWishlist(userDetails.getUsername(), productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/share/{shareableLink}")
    public ResponseEntity<WishlistResponse> getWishlistByShareableLink(@PathVariable String shareableLink) {
        return ResponseEntity.ok(wishlistService.getWishlistByShareableLink(shareableLink));
    }
}