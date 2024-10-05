package org.arpha.mapper;

import org.arpha.dto.product.CategoryDto;
import org.arpha.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Category toCategory(CategoryDto categoryDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Category toCategory(String name);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryDto toCategoryDto(Category category);

}
