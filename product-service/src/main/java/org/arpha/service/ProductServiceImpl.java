package org.arpha.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.audit.Action;
import org.arpha.dto.audit.reponse.AuditResponse;
import org.arpha.dto.media.enums.TargetType;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.media.response.FileResponse;
import org.arpha.dto.order.request.CreateOrderItem;
import org.arpha.dto.order.response.OrderAnalyticsDto;
import org.arpha.dto.product.request.CreateProductRequest;
import org.arpha.dto.product.request.CreateProductRequest.ProductFileRequest;
import org.arpha.dto.product.request.UpdateProductRequest;
import org.arpha.dto.product.response.CreateProductResponse;
import org.arpha.dto.product.response.GetProductListInfo;
import org.arpha.dto.product.response.ProductResponse;
import org.arpha.dto.product.response.RecommendationReason;
import org.arpha.dto.product.response.RecommendedProductResponse;
import org.arpha.dto.user.response.UserResponse;
import org.arpha.entity.Product;
import org.arpha.exception.CreateEntityException;
import org.arpha.exception.ProductNotFoundException;
import org.arpha.exception.UpdateEntityException;
import org.arpha.mapper.ProductMapper;
import org.arpha.mapper.helper.ProductMapperHelper;
import org.arpha.repository.ProductRepository;
import org.arpha.utils.Boxed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with %d id not found!";

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductMapperHelper productMapperHelper;
    private final MediaService mediaService;
    private final AuditService auditService;
    private final OrderService orderService;
    private final UserService userService;

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
    @Transactional
    public ProductResponse findProductById(long id) {
        return Boxed
                .of(id)
                .flatOpt(productRepository::findById)
                .mapToBoxed(productMapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_MESSAGE.formatted(id)));
    }

    @Override
    @Transactional 
    public Page<ProductResponse> findAllProducts(Predicate predicate, Pageable pageable) {
        return productRepository.findAll(predicate, pageable).map(productMapper::toProductResponse);
    }

    @Override
    @Transactional 
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


    @Override
    @Transactional
    public List<RecommendedProductResponse> getRecommendationsForUser(UserDetails user) {
        if (user == null) {
            // Logic for unauthenticated users: recommend most popular products based on views
            Page<AuditResponse> mostViewedProducts = auditService.findAll(
                    Expressions.enumPath(Action.class, "action").eq(Action.FIND_PRODUCT_BY_ID),
                    PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"))
            );
            return mostViewedProducts.stream()
                    .map(audit -> findProductById(audit.getTargetId()))
                    .map(product -> new RecommendedProductResponse(product, RecommendationReason.BASED_ON_GENRE))
                    .toList();
        }

        Long userId = userService.findUserByEmail(user.getUsername()).getId();

        // --- Logic for Authenticated Users ---

        // 1. Fetch user history
        List<Long> viewedProductIds = auditService.findAll(
                Expressions.allOf(
                        Expressions.enumPath(Action.class, "action").eq(Action.FIND_PRODUCT_BY_ID),
                        Expressions.numberPath(Long.class, "userId").eq(userId)
                ), PageRequest.of(0, 100)
        ).stream().map(AuditResponse::getTargetId).toList();

        List<OrderAnalyticsDto> userOrders = orderService.findAllByUserId(userId, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Long> orderedProductIds = userOrders.stream()
                .flatMap(order -> order.getOrderedItems().stream())
                .map(OrderAnalyticsDto.OrderItemAnalyticsDto::getProductId)
                .toList();

        Set<Long> allInteractedProductIds = Stream.concat(orderedProductIds.stream(), viewedProductIds.stream())
                .collect(Collectors.toSet());

        if (allInteractedProductIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. Build User Profile
        Map<String, Double> userProfile = buildUserProfile(orderedProductIds, viewedProductIds);

        // 3. Fetch Candidate Products
        Page<Product> candidateProducts = productRepository.findAll(
                Expressions.asBoolean(true).isTrue(), PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        // 4. Score and Rank Recommendations
        return candidateProducts.stream()
                .filter(p -> !allInteractedProductIds.contains(p.getId()))
                .map(p -> scoreProduct(p, userProfile))
                .sorted((r1, r2) -> Double.compare(r2.getScore(), r1.getScore()))
                .limit(5)
                .map(Recommendation::asRecommendedProductResponse)
                .collect(Collectors.toList());
    }

    private Map<String, Double> buildUserProfile(List<Long> orderedProductIds, List<Long> viewedProductIds) {
        Map<String, Double> profile = new HashMap<>();

        for (Long productId : orderedProductIds) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                updateProfile(profile, product, 2.0); // Higher score for ordered items
            }
        }
        for (Long productId : viewedProductIds) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                updateProfile(profile, product, 1.0); // Standard score for viewed items
            }
        }
        return profile;
    }

    private void updateProfile(Map<String, Double> profile, Product product, double weight) {
        product.getGenres().forEach(genre -> profile.merge("genre_" + genre.getName(), weight, Double::sum));
        product.getCategories().forEach(category -> profile.merge("category_" + category.getName(), weight, Double::sum));
        if (product.getAuthor() != null) {
            profile.merge("author_" + product.getAuthor(), weight, Double::sum);
        }
    }

    private Recommendation scoreProduct(Product product, Map<String, Double> userProfile) {
        double score = 0;
        RecommendationReason reason = RecommendationReason.BASED_ON_GENRE; // Default reason

        for (String genre : product.getGenres().stream().map(g -> "genre_" + g.getName()).toList()) {
            score += userProfile.getOrDefault(genre, 0.0);
        }
        for (String category : product.getCategories().stream().map(c -> "category_" + c.getName()).toList()) {
            score += userProfile.getOrDefault(category, 0.0);
        }
        if (product.getAuthor() != null) {
            score += userProfile.getOrDefault("author_" + product.getAuthor(), 0.0);
        }

        return new Recommendation(productMapper.toProductResponse(product), score, reason);
    }

    // Helper class for scoring
    private static class Recommendation {
        private final ProductResponse product;
        private final double score;
        private final RecommendationReason reason;

        public Recommendation(ProductResponse product, double score, RecommendationReason reason) {
            this.product = product;
            this.score = score;
            this.reason = reason;
        }

        public double getScore() {
            return score;
        }

        public RecommendedProductResponse asRecommendedProductResponse() {
            return new RecommendedProductResponse(product, reason);
        }
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
