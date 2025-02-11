package org.arpha.mapper;

import lombok.Data;
import org.arpha.dto.product.request.CreateProductRequest;
import org.arpha.dto.product.request.UpdateProductRequest;
import org.arpha.dto.product.response.GetProductListInfo;
import org.arpha.dto.product.response.ProductResponse;
import org.arpha.entity.Product;
import org.arpha.mapper.helper.ProductMapperHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = ProductMapperHelper.class)
public interface ProductMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "playerNumber", source = "playerNumber")
    @Mapping(target = "playTime", source = "playTime")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "rulesLink", source = "rulesLink")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "toStringGenres")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toStringCategories")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "width", source = "weight")
    @Mapping(target = "height", source = "height")
    @Mapping(target = "weight", source = "weight")
    @Mapping(target = "type.name", source = "type.name")
    @Mapping(target = "type.dimension.width", source = "type.dimension.width")
    @Mapping(target = "type.dimension.height", source = "type.dimension.height")
    @Mapping(target = "type.dimension.weight", source = "type.dimension.weight")
    @Mapping(target = "mainImgLink", source = "product", qualifiedByName = "toMainImgLink")
    @Mapping(target = "productPhotos", source = "product", qualifiedByName = "toProductPhotos")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "type.id", source = "type.id")
    @Mapping(target = "type.name", source = "type.name")
    @Mapping(target = "type.dimension.width", source = "type.dimension.width")
    @Mapping(target = "type.dimension.height", source = "type.dimension.height")
    @Mapping(target = "type.dimension.weight", source = "type.dimension.weight")
    @Mapping(target = "playerNumber", source = "playerNumber")
    @Mapping(target = "playTime", source = "playTime")
    @Mapping(target = "width", source = "weight")
    @Mapping(target = "height", source = "height")
    @Mapping(target = "weight", source = "weight")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "rulesLink", source = "rulesLink")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "toStringGenres")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toStringCategories")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "updatedBy", source = "updatedBy")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    ProductResponse toAdminProductResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "type", source = "productTypeId", qualifiedByName = "toProductType")
    @Mapping(target = "playerNumber", source = "playerNumber")
    @Mapping(target = "playTime", source = "playTime")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "width", source = "weight")
    @Mapping(target = "height", source = "height")
    @Mapping(target = "weight", source = "weight")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "toGenres")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toCategories")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toProduct(CreateProductRequest createProductRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "mainImgLink", source = "product", qualifiedByName = "toMainImgLink")
    GetProductListInfo toGetProductListInfo(Product product);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "type", source = "productTypeId", qualifiedByName = "toProductType")
    @Mapping(target = "playerNumber", source = "playerNumber")
    @Mapping(target = "playTime", source = "playTime")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "rulesLink", source = "rulesLink")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "width", source = "weight")
    @Mapping(target = "height", source = "height")
    @Mapping(target = "weight", source = "weight")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "toGenres")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toCategories")
    void update(@MappingTarget Product product, UpdateProductRequest updateProductRequest);
}
