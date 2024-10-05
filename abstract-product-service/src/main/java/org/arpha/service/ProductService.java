package org.arpha.service;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.product.request.CreateProductRequest;
import org.arpha.dto.product.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest createProductRequest);
    void deleteProduct(long id);
    ProductResponse findProductById(long id);
    Page<ProductResponse> findAllProducts(Predicate predicate, Pageable pageable);
    ProductResponse findAdminProductById(long id);
    Page<ProductResponse> findAdminAllProducts(Predicate predicate, Pageable pageable);
    ProductResponse addGenre(long id, Set<String> genres);
    ProductResponse addCategory(long id, Set<String> categories);
    boolean containCategoryAnyProduct(long categoryId);
    boolean containGenreAnyProduct(long genreId);
    boolean existProductById(long productId);
}
