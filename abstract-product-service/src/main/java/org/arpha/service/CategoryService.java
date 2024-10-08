package org.arpha.service;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.product.request.CreateCategoryRequest;
import org.arpha.dto.product.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest);
    void deleteCategory(long id);
    Page<CategoryResponse> getAllCategories(Predicate predicate, Pageable pageable);

}
