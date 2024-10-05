package org.arpha.service;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.product.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    void deleteCategory(long id);
    Page<CategoryDto> getAllCategories(Predicate predicate, Pageable pageable);

}
