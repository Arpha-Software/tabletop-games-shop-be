package org.arpha.mapper;

import org.arpha.dto.media.response.FileResponse;
import org.arpha.dto.product.request.CreateProductRequest;
import org.arpha.dto.product.request.UpdateProductRequest;
import org.arpha.dto.product.response.*;
import org.arpha.entity.Product;
import org.arpha.mapper.helper.ProductMapperHelper;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = ProductMapperHelper.class)
public interface ProductMapper {

    /**
     * A context object to hold pre-fetched image links, avoiding the N+1 problem in list mappings.
     */
    record ImageLinksContext(Map<Long, List<String>> mainImageLinks, Map<Long, List<String>> otherImageLinks) {}

    // --- Mappings to the new OOP-style ProductResponse ---

    /**
     * Un-optimized mapping for single product lookups (e.g., findById).
     * This will make individual calls to the media service.
     *
     * @param product The source Product entity.
     * @return A fully mapped ProductResponse DTO.
     */
    @Mapping(target = "gameDetails", source = "product")
    @Mapping(target = "classification", source = "product")
    @Mapping(target = "publicationDetails", source = "product")
    @Mapping(target = "media", source = "product")
    @Mapping(target = "addons", source = "addons")
    ProductResponse toProductResponse(Product product);

    /**
     * Optimized mapping for lists of products.
     * This method accepts pre-fetched image links via the context object to prevent N+1 calls.
     *
     * @param product The source Product entity.
     * @param context The context object with pre-fetched image links.
     * @return A fully mapped ProductResponse DTO.
     */
    @Mapping(target = "gameDetails", source = "product")
    @Mapping(target = "classification", source = "product")
    @Mapping(target = "publicationDetails", source = "product")
    @Mapping(target = "media", expression = "java(toMediaDetailsOptimized(product, context))")
    @Mapping(target = "addons", source = "addons")
    ProductResponse toProductResponse(Product product, @Context ImageLinksContext context);



    @Mapping(target = "gameDetails", source = "product")
    @Mapping(target = "classification", source = "product")
    @Mapping(target = "publicationDetails", source = "product")
    @Mapping(target = "media", source = "product")
    AddonResponse toAddonResponse(Product product);

    List<AddonResponse> toAddonResponseList(Set<Product> products);

    @Mapping(target = "players", expression = "java(product.getMinPlayerNumber() != null && product.getMaxPlayerNumber() != null ? product.getMinPlayerNumber() + \"-\" + product.getMaxPlayerNumber() : null)")
    @Mapping(target = "age", expression = "java(product.getMinAge() != null ? product.getMinAge() + \"+\" : null)")
    @Mapping(target = "playTime", expression = "java(product.getMinPlayTime() != null && product.getMaxPlayTime() != null ? product.getMinPlayTime() + \"-\" + product.getMaxPlayTime() + \" min.\" : null)")
    GameDetails toGameDetails(Product product);

    @Mapping(target = "genres", source = "genres", qualifiedByName = "toStringGenres")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toStringCategories")
    ClassificationDetails toClassificationDetails(Product product);

    PublicationDetails toPublicationDetails(Product product);

    @Mapping(target = "mainImgLink", source = "product", qualifiedByName = "toMainImgLink")
    @Mapping(target = "photos", source = "product", qualifiedByName = "toProductPhotos")
    MediaDetails toMediaDetails(Product product);

    /**
     * Helper method for the optimized mapping. It builds MediaDetails from the pre-fetched context.
     */
    default MediaDetails toMediaDetailsOptimized(Product product, @Context ImageLinksContext context) {
        String mainImgLink = context.mainImageLinks()
                .getOrDefault(product.getId(), Collections.emptyList())
                .stream().findFirst().orElse(null);

        List<String> photos = context.otherImageLinks()
                .getOrDefault(product.getId(), Collections.emptyList());

        return MediaDetails.builder()
                .mainImgLink(mainImgLink)
                .photos(photos)
                .rulesLink(product.getRulesLink())
                .build();
    }

    ProductSearchResponse toProductSearchResponse(Product product);


    // --- Mappings from Request DTOs to the Product Entity ---

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", source = "productTypeId", qualifiedByName = "toProductType")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "toGenres")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toCategories")
    @Mapping(target = "mechanics", source = "mechanics")
    @Mapping(target = "dimension.width", source = "width")
    @Mapping(target = "dimension.height", source = "height")
    @Mapping(target = "dimension.weight", source = "weight")
    @Mapping(target = "dimension.length", source = "length")
    @Mapping(target = "minPlayerNumber", source = "minPlayerNumber")
    @Mapping(target = "maxPlayerNumber", source = "maxPlayerNumber")
    @Mapping(target = "minPlayTime", source = "minPlayTime")
    @Mapping(target = "maxPlayTime", source = "maxPlayTime")
    @Mapping(target = "minAge", source = "minAge")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "publisher", source = "publisher")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "bggRating", source = "bggRating")
    @Mapping(target = "complexity", source = "complexity")
    @Mapping(target = "components", source = "components")
    Product toProduct(CreateProductRequest createProductRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", source = "productTypeId", qualifiedByName = "toProductType")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "toGenres")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toCategories")
    @Mapping(target = "mechanics", source = "mechanics")
    @Mapping(target = "dimension.width", source = "width")
    @Mapping(target = "dimension.height", source = "height")
    @Mapping(target = "dimension.weight", source = "weight")
    @Mapping(target = "dimension.length", source = "length")
    @Mapping(target = "minPlayerNumber", source = "minPlayerNumber")
    @Mapping(target = "maxPlayerNumber", source = "maxPlayerNumber")
    @Mapping(target = "minPlayTime", source = "minPlayTime")
    @Mapping(target = "maxPlayTime", source = "maxPlayTime")
    @Mapping(target = "minAge", source = "minAge")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "publisher", source = "publisher")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "bggRating", source = "bggRating")
    @Mapping(target = "complexity", source = "complexity")
    @Mapping(target = "components", source = "components")
    void update(@MappingTarget Product product, UpdateProductRequest updateProductRequest);


    // --- Other existing mappings ---

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "mainImgLink", source = "product", qualifiedByName = "toMainImgLink")
    GetProductListInfo toGetProductListInfo(Product product);

    /**
     * Corrected method: This now maps a created Product and its uploaded files
     * to the rich, OOP-style ProductResponse.
     *
     * @param product       The newly saved Product entity.
     * @param fileResponses A list of FileResponse DTOs from the media service.
     * @return A complete ProductResponse object.
     */
    @Mapping(target = "gameDetails", source = "product")
    @Mapping(target = "classification", source = "product")
    @Mapping(target = "publicationDetails", source = "product")
    @Mapping(target = "media", source = "product")
    // Note: We ignore fileResponses here as the media object is now built from the product entity itself.
    // The links are generated by the helper methods called within toMediaDetails.
    ProductResponse toCreateProductResponse(Product product, @Context List<FileResponse> fileResponses);

}
