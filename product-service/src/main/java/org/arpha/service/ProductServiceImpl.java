package org.arpha.service;

import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.enums.TargetType;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.media.response.FileResponse;
import org.arpha.dto.product.request.CreateProductRequest;
import org.arpha.dto.product.response.ProductDetailsResponse;
import org.arpha.dto.product.response.ProductResponse;
import org.arpha.entity.Product;
import org.arpha.exception.CreateEntityException;
import org.arpha.exception.ProductNotFoundException;
import org.arpha.mapper.ProductMapper;
import org.arpha.mapper.helper.ProductMapperHelper;
import org.arpha.repository.ProductRepository;
import org.arpha.utils.Boxed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with %d id not found!";

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final MediaService mediaService;
    private final ProductMapperHelper productMapperHelper;

    @Override
    @Transactional
    public ProductDetailsResponse createProduct(CreateProductRequest createProductRequest) {
        return Boxed
                .of(createProductRequest)
                .filter(request -> !productRepository.existsByName(request.getName()))
                .mapToBoxed(this::saveProduct)
                .orElseThrow(() -> new CreateEntityException(("Unable to create product, because product with the %s name" +
                                                              " already exists!").formatted(createProductRequest.getName())));
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDetailsResponse findProductById(long id) {
        return Boxed
                .of(id)
                .flatOpt(productRepository::findById)
                .mapToBoxed(productMapper::toProductDetailsResponse)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Override
    public Page<ProductResponse> findAllProducts(Predicate predicate, Pageable pageable) {
        return productRepository.findAll(predicate, pageable)
                .map(productMapper::toProductResponse);
    }

    @Override
    public ProductDetailsResponse findAdminProductById(long id) {
        return Boxed
                .of(id)
                .flatOpt(productRepository::findById)
                .mapToBoxed(productMapper::toAdminProductResponse)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Override
    public Page<ProductDetailsResponse> findAdminAllProducts(Predicate predicate, Pageable pageable) {
        return productRepository.findAll(predicate, pageable).map(productMapper::toAdminProductResponse);
    }

    @Override
    public ProductDetailsResponse addGenre(long id, Set<String> genres) {
        return Boxed
                .of(id)
                .flatOpt(productRepository::findById)
                .doWith(product -> product.getGenres().addAll(productMapperHelper.toGenres(genres)))
                .mapToBoxed(productRepository::save)
                .mapToBoxed(productMapper::toProductDetailsResponse)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Override
    public ProductDetailsResponse addCategory(long id, Set<String> categories) {
        return Boxed
                .of(id)
                .flatOpt(productRepository::findById)
                .doWith(product -> product.getCategories().addAll(productMapperHelper.toCategories(categories)))
                .mapToBoxed(productRepository::save)
                .mapToBoxed(productMapper::toProductDetailsResponse)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Override
    public boolean containCategoryAnyProduct(long categoryId) {
        return productRepository.containsCategoryCount(categoryId) == 0;
    }

    @Override
    public boolean containGenreAnyProduct(long genreId) {
        return productRepository.containsGenreCount(genreId) == 0;
    }

    @Override
    public boolean existProductById(long productId) {
        return productRepository.existsById(productId);
    }

    private ProductDetailsResponse saveProduct(CreateProductRequest createProductRequest) {
        Product product = productRepository.save(productMapper.toProduct(createProductRequest));

        List<FileResponse> fileResponses = createProductRequest.getFileUploadRequests().stream()
                .map(request -> new FileUploadRequest(request.getType(), request.getFileSize(), product.getId(), TargetType.PRODUCT))
                .map(mediaService::upload)
                .toList();

        return productMapper.toProductDetailsResponse(product, fileResponses);
    }

}
