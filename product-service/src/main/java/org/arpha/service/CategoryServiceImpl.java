package org.arpha.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.product.CategoryDto;
import org.arpha.exception.CreateCategoryException;
import org.arpha.exception.DeleteEntityException;
import org.arpha.mapper.CategoryMapper;
import org.arpha.repository.CategoryRepository;
import org.arpha.utils.Boxed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductService productService;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        return Boxed
                .of(categoryDto)
                .mapToBoxed(categoryMapper::toCategory)
                .mapToBoxed(categoryRepository::save)
                .mapToBoxed(categoryMapper::toCategoryDto)
                .orElseThrow(() -> new CreateCategoryException("Unable to create category!"));
    }

    @Override
    public void deleteCategory(long id) {
        Boxed
                .of(id)
                .filter(productService::containCategoryAnyProduct)
                .ifPresentOrElseThrow(categoryRepository::deleteById,
                        () -> new DeleteEntityException("Category wasn't deleted because some product has it"));
    }

    @Override
    public Page<CategoryDto> getAllCategories(Predicate predicate, Pageable pageable) {
        return categoryRepository.findAll(predicate, pageable).map(categoryMapper::toCategoryDto);
    }

}
