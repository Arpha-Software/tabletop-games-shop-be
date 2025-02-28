package org.arpha.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.product.request.CreateProductRequest;
import org.arpha.dto.product.request.UpdateProductRequest;
import org.arpha.dto.product.response.CreateProductResponse;
import org.arpha.dto.product.response.GetProductListInfo;
import org.arpha.dto.product.response.ProductResponse;
import org.arpha.entity.Product;
import org.arpha.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public CreateProductResponse createProduct(@RequestBody CreateProductRequest createProductRequest) {
        return productService.createProduct(createProductRequest);
    }

    @GetMapping
    public Page<GetProductListInfo> findAllProducts(@QuerydslPredicate(root = Product.class) Predicate predicate, Pageable pageable) {
        return productService.findAllProducts(predicate, pageable);
    }

    @GetMapping("/{id}")
    public ProductResponse findProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable long id) {
        productService.deleteProduct(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public Page<ProductResponse> findAllAdminProducts(@QuerydslPredicate(root = Product.class) Predicate predicate, Pageable pageable) {
        return productService.findAdminAllProducts(predicate, pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/admin")
    public ProductResponse findAdminProductById(@PathVariable Long id) {
        return productService.findAdminProductById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/genres")
    public ProductResponse addGenre(@RequestBody Set<String> genres, @PathVariable long id) {
        return productService.addGenre(id, genres);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/categories")
    public ProductResponse addCategory(@RequestBody Set<String> genres, @PathVariable long id) {
        return productService.addCategory(id, genres);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable long id, @RequestBody @Valid UpdateProductRequest updateProductRequest) {
        return productService.update(id, updateProductRequest);
    }
}
