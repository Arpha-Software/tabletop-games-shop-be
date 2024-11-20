package org.arpha.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.product.request.CreateProductRequest;
import org.arpha.dto.product.response.ProductDetailsResponse;
import org.arpha.dto.product.response.ProductResponse;
import org.arpha.entity.Product;
import org.arpha.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ProductDetailsResponse createProduct(@RequestBody @Valid CreateProductRequest createProductRequest) {
        return productService.createProduct(createProductRequest);
    }

    @GetMapping
    public Page<ProductResponse> findAllProducts(@QuerydslPredicate(root = Product.class) Predicate predicate, Pageable pageable) {
        return productService.findAllProducts(predicate, pageable);
    }

    @GetMapping("/{id}")
    public ProductDetailsResponse findProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable long id) {
        productService.deleteProduct(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public Page<ProductDetailsResponse> findAllAdminProducts(@QuerydslPredicate(root = Product.class) Predicate predicate, Pageable pageable) {
        return productService.findAdminAllProducts(predicate, pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/admin")
    public ProductDetailsResponse findAdminProductById(@PathVariable Long id) {
        return productService.findAdminProductById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/genres")
    public ProductDetailsResponse addGenre(@RequestBody Set<String> genres, @PathVariable long id) {
        return productService.addGenre(id, genres);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/categories")
    public ProductDetailsResponse addCategory(@RequestBody Set<String> genres, @PathVariable long id) {
        return productService.addCategory(id, genres);
    }

}
