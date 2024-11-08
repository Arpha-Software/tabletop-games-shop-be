package org.arpha.mapper;

import org.arpha.dto.product.request.CreateProductRequest;
import org.arpha.dto.product.response.GetProductListInfo;
import org.arpha.dto.product.response.ProductResponse;
import org.arpha.entity.Product;
import org.arpha.mapper.helper.ProductMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = ProductMapperHelper.class)
public interface ProductMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
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
    @Mapping(target = "mainImgLink", source = "product", qualifiedByName = "toMainImgLink")
    @Mapping(target = "productPhotos", source = "product", qualifiedByName = "toProductPhotos")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "playerNumber", source = "playerNumber")
    @Mapping(target = "playTime", source = "playTime")
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
    @Mapping(target = "type", source = "type")
    @Mapping(target = "playerNumber", source = "playerNumber")
    @Mapping(target = "playTime", source = "playTime")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "toGenres")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toCategories")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toProduct(CreateProductRequest createProductRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "mainImgLink", source = "product", qualifiedByName = "toMainImgLink")
    GetProductListInfo toGetProductListInfo(Product product);
}
