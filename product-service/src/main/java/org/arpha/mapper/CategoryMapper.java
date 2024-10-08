package org.arpha.mapper;

import org.arpha.dto.product.request.CreateCategoryRequest;
import org.arpha.dto.product.response.CategoryResponse;
import org.arpha.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Category toCategory(CreateCategoryRequest createCategoryRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Category toCategory(String name);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryResponse toCategoryDto(Category category);

}
