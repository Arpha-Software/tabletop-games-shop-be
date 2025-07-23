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
            // --- Product Name (String) ---
            @Parameter(name = "name.eq", description = "Filter by exact product name (case-insensitive).", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "name.ne", description = "Filter by non-exact product name (case-insensitive).", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "name.contains", description = "Filter for names containing the value (case-insensitive). Example: Catan", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "name.in", description = "Filter for names in a list (comma-separated). Example: Catan,Risk", in = ParameterIn.QUERY, schema = @Schema(type = "string")),

            // --- Price (Numeric) ---
            @Parameter(name = "price.eq", description = "Filter by exact price.", in = ParameterIn.QUERY, schema = @Schema(type = "number")),
            @Parameter(name = "price.ne", description = "Filter by non-exact price.", in = ParameterIn.QUERY, schema = @Schema(type = "number")),
            @Parameter(name = "price.gt", description = "Filter for price greater than the value.", in = ParameterIn.QUERY, schema = @Schema(type = "number")),
            @Parameter(name = "price.gte", description = "Filter for price greater than or equal to the value.", in = ParameterIn.QUERY, schema = @Schema(type = "number")),
            @Parameter(name = "price.lt", description = "Filter for price less than the value.", in = ParameterIn.QUERY, schema = @Schema(type = "number")),
            @Parameter(name = "price.lte", description = "Filter for price less than or equal to the value.", in = ParameterIn.QUERY, schema = @Schema(type = "number")),
            @Parameter(name = "price.in", description = "Filter for price in a list (comma-separated). Example: 29.99,49.99", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "price.between", description = "Filter for price between two values (inclusive, comma-separated). Example: 50,100", in = ParameterIn.QUERY, schema = @Schema(type = "string")),

            // --- Player Count (Numeric) ---
            @Parameter(name = "minPlayerNumber.gte", description = "Filter for products that support at least this many players.", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "maxPlayerNumber.lte", description = "Filter for products with a maximum player count up to this value.", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "minPlayerNumber.eq", description = "Filter by exact minimum player count.", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "maxPlayerNumber.eq", description = "Filter by exact maximum player count.", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),

            // --- Play Time (Numeric) ---
            @Parameter(name = "minPlayTime.gte", description = "Filter by minimum play time in minutes.", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "maxPlayTime.lte", description = "Filter by maximum play time in minutes.", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),

            // --- Age (Numeric) ---
            @Parameter(name = "minAge.gte", description = "Filter by minimum recommended age.", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),

            // --- BGG Rating & Complexity (Numeric) ---
            @Parameter(name = "bggRating.between", description = "Filter by BoardGameGeek rating between two values. Example: 7.5,9.0", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "complexity.between", description = "Filter by complexity rating between two values. Example: 2.0,3.5", in = ParameterIn.QUERY, schema = @Schema(type = "string")),

            // --- Language (String) ---
            @Parameter(name = "language.eq", description = "Filter by exact language.", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "language.in", description = "Filter for languages in a list. Example: English,Polish", in = ParameterIn.QUERY, schema = @Schema(type = "string")),

            // --- Publisher (String) ---
            @Parameter(name = "publisher.contains", description = "Filter for publisher names containing the value.", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "publisher.in", description = "Filter for publishers in a list.", in = ParameterIn.QUERY, schema = @Schema(type = "string")),

            // --- Author (String) ---
            @Parameter(name = "author.contains", description = "Filter for author names containing the value.", in = ParameterIn.QUERY, schema = @Schema(type = "string")),

            // --- Collections & Relationships ---
            @Parameter(name = "mechanics.in", description = "Filter for products with ANY of the given mechanics (comma-separated). Example: Deckbuilding,Worker Placement", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "mechanics.all", description = "Filter for products with ALL of the given mechanics (comma-separated). Example: Deckbuilding,Area Control", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "categories.id.in", description = "Filter for products in ANY of the given category IDs (comma-separated).", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "categories.name.in", description = "Filter for products in ANY of the given category names (comma-separated).", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "genres.id.in", description = "Filter for products in ANY of the given genre IDs (comma-separated).", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "genres.name.in", description = "Filter for products in ANY of the given genre names (comma-separated).", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "addons.id.in", description = "Filter for products that have ANY of the given addon IDs (comma-separated).", in = ParameterIn.QUERY, schema = @Schema(type = "string")),

            // --- Product Type (Nested) ---
            @Parameter(name = "type.id.eq", description = "Filter by exact product type ID.", in = ParameterIn.QUERY, schema = @Schema(type = "integer", format = "int64")),
            @Parameter(name = "type.name.eq", description = "Filter by exact product type name.", in = ParameterIn.QUERY, schema = @Schema(type = "string")),

            // --- Date Fields ---
            @Parameter(name = "createdAt.between", description = "Filter by creation timestamp between two dates (ISO 8601, comma-separated). Example: 2025-01-01T00:00:00Z,2025-07-23T23:59:59Z", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "createdAt.gte", description = "Filter for products created on or after a timestamp (ISO 8601).", in = ParameterIn.QUERY, schema = @Schema(type = "string", format = "date-time")),
            @Parameter(name = "createdAt.lte", description = "Filter for products created on or before a timestamp (ISO 8601).", in = ParameterIn.QUERY, schema = @Schema(type = "string", format = "date-time")),

            // --- General ---
            @Parameter(name = "page", description = "Page number you want to retrieve (0..N).", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")),
            @Parameter(name = "size", description = "Number of records per page.", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "20")),
            @Parameter(name = "sort", description = "Sorting criteria in the format: property,(asc|desc). Default sort is ascending. Multiple sort criteria are supported.", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
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
