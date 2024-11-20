package org.arpha.service;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.product.request.CreateProductRequest;
import org.arpha.dto.product.response.ProductDetailsResponse;
import org.arpha.dto.product.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface ProductService {

    ProductDetailsResponse createProduct(CreateProductRequest createProductRequest);
    void deleteProduct(long id);
    ProductDetailsResponse findProductById(long id);
    Page<ProductResponse> findAllProducts(Predicate predicate, Pageable pageable);
    ProductDetailsResponse findAdminProductById(long id);
    Page<ProductDetailsResponse> findAdminAllProducts(Predicate predicate, Pageable pageable);
    ProductDetailsResponse addGenre(long id, Set<String> genres);
    ProductDetailsResponse addCategory(long id, Set<String> categories);
    boolean containCategoryAnyProduct(long categoryId);
    boolean containGenreAnyProduct(long genreId);
    boolean existProductById(long productId);
}
