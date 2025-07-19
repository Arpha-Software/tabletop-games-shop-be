package org.arpha.service;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.product.response.WishlistResponse;
import org.arpha.entity.Product;
import org.arpha.entity.Wishlist;
import org.arpha.exception.ProductNotFoundException;
import org.arpha.mapper.WishlistMapper;
import org.arpha.repository.ProductRepository;
import org.arpha.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final WishlistMapper wishlistMapper;

    @Override
    public WishlistResponse getWishlistByUserId(String email) {
        Wishlist wishlist = wishlistRepository.findByUserEmail(email)
                .orElseGet(() -> createWishlistForUser(email));
        return wishlistMapper.toWishlistResponse(wishlist);
    }

    @Override
    public WishlistResponse addProductToWishlist(String email, Long productId) {
        Wishlist wishlist = wishlistRepository.findByUserEmail(email)
                .orElseGet(() -> createWishlistForUser(email));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        wishlist.getProducts().add(product);
        return wishlistMapper.toWishlistResponse(wishlistRepository.save(wishlist));
    }

    @Override
    public void removeProductFromWishlist(String email, Long productId) {
        Wishlist wishlist = wishlistRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        wishlist.getProducts().remove(product);
        wishlistRepository.save(wishlist);
    }

    @Override
    public WishlistResponse getWishlistByShareableLink(String shareableLink) {
        return wishlistRepository.findByShareableLink(shareableLink)
                .map(wishlistMapper::toWishlistResponse)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
    }

    private Wishlist createWishlistForUser(String userEmail) {
        Wishlist newWishlist = new Wishlist();
        newWishlist.setUserEmail(userEmail);
        newWishlist.setProducts(new HashSet<>());
        return wishlistRepository.save(newWishlist);
    }
}
