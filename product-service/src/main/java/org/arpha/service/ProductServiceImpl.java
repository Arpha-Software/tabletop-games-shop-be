package org.arpha.service;

import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.enums.TargetType;
import org.arpha.dto.order.request.CreateOrderItem;
import org.arpha.dto.product.request.CreateProductRequest;
import org.arpha.dto.product.response.GetProductListInfo;
import org.arpha.dto.product.response.ProductResponse;
import org.arpha.entity.Product;
import org.arpha.exception.CreateEntityException;
import org.arpha.exception.ProductNotFoundException;
import org.arpha.exception.UpdateEntityException;
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
    private final ProductMapperHelper productMapperHelper;
    private final MediaService mediaService;

    @Override
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        return Boxed
                .of(createProductRequest)
                .filter(createProductRequest1 -> !productRepository.existsByName(createProductRequest1.getName()))
                .mapToBoxed(productMapper::toProduct)
                .mapToBoxed(productRepository::save)
                .mapToBoxed(productMapper::toProductResponse)
                .orElseThrow(() -> new CreateEntityException(("Unable to create product, because product with the %s name" +
                                                              " already exists!").formatted(createProductRequest.getName())));
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
        mediaService.deleteAllByTargetIdAndType(id, TargetType.PRODUCT);
        mediaService.deleteAllByTargetIdAndType(id, TargetType.PRODUCT_MAIN_IMG);
    }

    @Override
    public ProductResponse findProductById(long id) {
        return Boxed
                .of(id)
                .flatOpt(productRepository::findById)
                .mapToBoxed(productMapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Override
    public Page<GetProductListInfo> findAllProducts(Predicate predicate, Pageable pageable) {
        return productRepository.findAll(predicate, pageable).map(productMapper::toGetProductListInfo);
    }

    @Override
    public ProductResponse findAdminProductById(long id) {
        return Boxed
                .of(id)
                .flatOpt(productRepository::findById)
                .mapToBoxed(productMapper::toAdminProductResponse)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Override
    public Page<ProductResponse> findAdminAllProducts(Predicate predicate, Pageable pageable) {
        return productRepository.findAll(predicate, pageable).map(productMapper::toAdminProductResponse);
    }

    @Override
    public ProductResponse addGenre(long id, Set<String> genres) {
        return Boxed
                .of(id)
                .flatOpt(productRepository::findById)
                .doWith(product -> product.getGenres().addAll(productMapperHelper.toGenres(genres)))
                .mapToBoxed(productRepository::save)
                .mapToBoxed(productMapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Override
    public ProductResponse addCategory(long id, Set<String> categories) {
        return Boxed
                .of(id)
                .flatOpt(productRepository::findById)
                .doWith(product -> product.getCategories().addAll(productMapperHelper.toCategories(categories)))
                .mapToBoxed(productRepository::save)
                .mapToBoxed(productMapper::toProductResponse)
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

    @Override
    public void updateQuantity(List<CreateOrderItem> items) {
        items.forEach(this::updateQuantity);
    }

    private void updateQuantity(CreateOrderItem item) {
        Boxed
                .of(item)
                .flatOpt(item1 -> productRepository.findById(item1.getProductId()))
                .filter(product ->  product.getQuantity() >= item.getQuantity())
                .doWith(product -> product.setQuantity(product.getQuantity() - item.getQuantity()))
                .mapToBoxed(productRepository::save)
                .orElseThrow(() -> new UpdateEntityException("Couldn't update product with %s id, because requires more amount then in store.".formatted(item.getQuantity())));
    }

}
