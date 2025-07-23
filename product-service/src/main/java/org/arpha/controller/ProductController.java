package org.arpha.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.product.request.CreateProductRequest;
import org.arpha.dto.product.request.UpdateProductRequest;
import org.arpha.dto.product.response.CreateProductResponse;
import org.arpha.dto.product.response.GetProductListInfo;
import org.arpha.dto.product.response.ProductResponse;
import org.arpha.dto.product.response.ProductSearchResponse;
import org.arpha.dto.product.response.RecommendedProductResponse;
import org.arpha.entity.Product;
import org.arpha.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
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
    @Parameters({
            @Parameter(name = "name", description = "Filter by product name (case-insensitive contains)", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "price", description = "Filter by price (exact match)", in = ParameterIn.QUERY, schema = @Schema(type = "number", format = "double")),
            @Parameter(name = "language", description = "Filter by product language (exact match)", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "minPlayerNumber", description = "Filter by minimum player number", in = ParameterIn.QUERY, schema = @Schema(type = "integer", format = "int32")),
            @Parameter(name = "maxPlayerNumber", description = "Filter by maximum player number", in = ParameterIn.QUERY, schema = @Schema(type = "integer", format = "int32")),
            @Parameter(name = "minPlayTime", description = "Filter by minimum playtime in minutes", in = ParameterIn.QUERY, schema = @Schema(type = "integer", format = "int32")),
            @Parameter(name = "maxPlayTime", description = "Filter by maximum playtime in minutes", in = ParameterIn.QUERY, schema = @Schema(type = "integer", format = "int32")),
            @Parameter(name = "minAge", description = "Filter by minimum age", in = ParameterIn.QUERY, schema = @Schema(type = "integer", format = "int32")),
            @Parameter(name = "publisher", description = "Filter by publisher name (case-insensitive contains)", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "author", description = "Filter by author name (case-insensitive contains)", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "bggRating", description = "Filter by BoardGameGeek rating", in = ParameterIn.QUERY, schema = @Schema(type = "number", format = "double")),
            @Parameter(name = "complexity", description = "Filter by complexity rating", in = ParameterIn.QUERY, schema = @Schema(type = "number", format = "double")),
            @Parameter(name = "components", description = "Filter by components (case-insensitive contains)", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "rulesLink", description = "Filter by rules link (case-insensitive contains)", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "description", description = "Filter by product description (case-insensitive contains)", in = ParameterIn.QUERY, schema = @Schema(type = "string")),

            // Nested Properties and Collections
            @Parameter(name = "categories.name", description = "Filter by category name (case-insensitive contains)", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "genres.name", description = "Filter by genre name (case-insensitive contains)", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "type.name", description = "Filter by product type name (e.g., 'Board Game', 'Card Game')", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "type.id", description = "Filter by product type ID", in = ParameterIn.QUERY, schema = @Schema(type = "integer", format = "int64")),
            @Parameter(name = "addons.name", description = "Filter by addon name (case-insensitive contains)", in = ParameterIn.QUERY, schema = @Schema(type = "string")), // Assuming Addon has a 'name' field

            // Nested Dimension properties
            @Parameter(name = "dimension.width", description = "Filter by product width", in = ParameterIn.QUERY, schema = @Schema(type = "number", format = "double")),
            @Parameter(name = "dimension.length", description = "Filter by product length", in = ParameterIn.QUERY, schema = @Schema(type = "number", format = "double")),
            @Parameter(name = "dimension.height", description = "Filter by product height", in = ParameterIn.QUERY, schema = @Schema(type = "number", format = "double")),
            @Parameter(name = "dimension.weight", description = "Filter by product weight", in = ParameterIn.QUERY, schema = @Schema(type = "number", format = "double")),

            // Derived/Aggregated fields (if Querydsl or custom service logic supports them)
            @Parameter(name = "reviewCount", description = "Filter by total review count", in = ParameterIn.QUERY, schema = @Schema(type = "integer", format = "int32")),
            @Parameter(name = "averageRating", description = "Filter by average customer rating", in = ParameterIn.QUERY, schema = @Schema(type = "number", format = "double")),

            // Date field
            @Parameter(name = "createdAt", description = "Filter by creation timestamp (ISO 8601 format, e.g., '2023-01-01T10:00:00Z')", in = ParameterIn.QUERY, schema = @Schema(type = "string", format = "date-time"))
    })
    public Page<ProductResponse> findAllProducts(@QuerydslPredicate(root = Product.class) Predicate predicate, Pageable pageable) {
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/addons")
    public ProductResponse addAddons(@RequestBody Set<Long> addonIds, @PathVariable long id) {
        return productService.addAddon(id, addonIds);
    }

    @GetMapping("/recommendations")
    public List<RecommendedProductResponse> getRecommendations() {
        UserDetails userDetails;
        try {
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            userDetails = null;
        }

        return productService.getRecommendationsForUser(userDetails);
    }

    @GetMapping("/search")
    public List<ProductSearchResponse> searchProducts(@RequestParam String query) {
        return productService.searchProducts(query);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/generate-fake-data")
    public ResponseEntity<Void> generateFakeProducts(@RequestParam(defaultValue = "50") int count) {
        productService.generateFakeProducts(count);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
