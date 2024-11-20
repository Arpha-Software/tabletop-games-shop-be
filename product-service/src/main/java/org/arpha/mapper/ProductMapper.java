package org.arpha.mapper;

import org.arpha.dto.media.response.FileResponse;
import org.arpha.dto.product.request.CreateProductRequest;
import org.arpha.dto.product.response.ProductDetailsResponse;
import org.arpha.dto.product.response.ProductResponse;
import org.arpha.entity.Product;
import org.arpha.mapper.helper.ProductMapperHelper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = ProductMapperHelper.class)
public interface ProductMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "playerNumber", source = "playerNumber")
    @Mapping(target = "playTime", source = "playTime")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "rulesLink", source = "rulesLink")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "toStringGenres")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toStringCategories")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "fileResponses", source = "product", qualifiedByName = "toProductFileResponses")
    ProductDetailsResponse toProductDetailsResponse(Product product);

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "type", source = "product.type")
    @Mapping(target = "playerNumber", source = "product.playerNumber")
    @Mapping(target = "playTime", source = "product.playTime")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "rulesLink", source = "product.rulesLink")
    @Mapping(target = "genres", source = "product.genres", qualifiedByName = "toStringGenres")
    @Mapping(target = "categories", source = "product.categories", qualifiedByName = "toStringCategories")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "fileResponses", source = "fileResponses")
    ProductDetailsResponse toProductDetailsResponse(Product product, List<FileResponse> fileResponses);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "toStringGenres")
    @Mapping(target = "firstImage", source = "product", qualifiedByName = "toSingleProductFileResponse")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "playerNumber", source = "playerNumber")
    @Mapping(target = "playTime", source = "playTime")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "rulesLink", source = "rulesLink")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "toStringGenres")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toStringCategories")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "updatedBy", source = "updatedBy")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    ProductDetailsResponse toAdminProductResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "playerNumber", source = "playerNumber")
    @Mapping(target = "playTime", source = "playTime")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "toGenres")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toCategories")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toProduct(CreateProductRequest createProductRequest);

}
