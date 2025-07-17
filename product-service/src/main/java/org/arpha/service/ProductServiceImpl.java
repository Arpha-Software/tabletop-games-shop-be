package org.arpha.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.enums.TargetType;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.media.response.FileResponse;
import org.arpha.dto.order.request.CreateOrderItem;
import org.arpha.dto.product.request.CreateProductRequest;
import org.arpha.dto.product.request.CreateProductRequest.ProductFileRequest;
import org.arpha.dto.product.request.UpdateProductRequest;
import org.arpha.dto.product.response.CreateProductResponse;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public ProductResponse createProduct(CreateProductRequest createProductRequest) { // Changed return type
        return Boxed
                .of(createProductRequest)
                .filter(request -> !productRepository.existsByName(request.getName()))
                .mapToBoxed(this::saveProduct) // This now returns ProductResponse
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
    @Transactional(readOnly = true)
    public ProductResponse findProductById(long id) {
        return Boxed
                .of(id)
                .flatOpt(productRepository::findById)
                .mapToBoxed(productMapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> findAllProducts(Predicate predicate, Pageable pageable) {
        return productRepository.findAll(predicate, pageable).map(productMapper::toProductResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> findAdminAllProducts(Predicate predicate, Pageable pageable) {
        return productRepository.findAll(predicate, pageable).map(productMapper::toProductResponse);
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

    @Override
    public ProductResponse update(long id, UpdateProductRequest updateProductRequest) {
        return Boxed
                .of(id)
                .flatOpt(productRepository::findById)
                .doWith(product -> productMapper.update(product, updateProductRequest))
                .mapToBoxed(productRepository::save)
                .mapToBoxed(productMapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Override
    public ProductResponse addAddon(long id, Set<Long> addonIds) {
        return Boxed
                .of(id)
                .flatOpt(productRepository::findById)
                .doWith(product -> {
                    Set<Product> addons = addonIds.stream()
                            .map(productRepository::findById)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(Collectors.toSet());
                    product.getAddons().addAll(addons);
                })
                .mapToBoxed(productRepository::save)
                .mapToBoxed(productMapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.formatted(id)));
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

    private ProductResponse saveProduct(CreateProductRequest createProductRequest) { // Changed return type
        Product product = productRepository.save(productMapper.toProduct(createProductRequest));

        // The file upload logic remains the same
        List<FileResponse> fileResponses = new ArrayList<>();
        List<ProductFileRequest> productFileRequests = createProductRequest.getFileUploadRequests();

        Optional<ProductFileRequest> mainImageRequest = productFileRequests.stream()
                .filter(pfr -> pfr.getIsMain() != null && pfr.getIsMain())
                .findFirst();

        if (mainImageRequest.isPresent()) {
            ProductFileRequest fileUploadRequest = mainImageRequest.get();
            FileResponse fileResponse = mediaService.upload(new FileUploadRequest(fileUploadRequest.getType(), fileUploadRequest.getFileSize(), product.getId(), TargetType.PRODUCT_MAIN_IMG, fileUploadRequest.getUuid()));
            fileResponses.add(fileResponse);
        }

        // Filter out the main image so it's not processed again
        List<ProductFileRequest> remainingFiles = productFileRequests.stream()
                .filter(pfr -> pfr.getIsMain() == null || !pfr.getIsMain())
                .collect(Collectors.toList());

        remainingFiles.stream()
                .map(request -> new FileUploadRequest(request.getType(), request.getFileSize(), product.getId(), TargetType.PRODUCT, request.getUuid()))
                .map(mediaService::upload)
                .forEach(fileResponses::add);

        // Use the correct mapper method that returns the rich ProductResponse
        return productMapper.toProductResponse(product);
    }
}
