package org.arpha.mapper;

import org.arpha.dto.product.request.ModifyProductTypeRequest;
import org.arpha.dto.product.response.GetProductTypeResponse;
import org.arpha.entity.ProductType;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductTypeMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "dimension.width", source = "width")
    @Mapping(target = "dimension.height", source = "height")
    @Mapping(target = "dimension.weight", source = "weight")
    @Mapping(target = "dimension.length", source = "length")

    ProductType toProductType(ModifyProductTypeRequest productTypeRequest);

    GetProductTypeResponse toGetProductTypeResponse(ProductType productType);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "dimension.width", source = "width")
    @Mapping(target = "dimension.height", source = "height")
    @Mapping(target = "dimension.weight", source = "weight")
    @Mapping(target = "dimension.length", source = "length")
    void update(@MappingTarget ProductType productType, ModifyProductTypeRequest modifyProductTypeRequest);

}
