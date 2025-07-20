package org.arpha.service;

import com.github.javafaker.Faker;
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
import org.arpha.dto.product.Dimension;
import org.arpha.dto.product.request.CreateProductRequest;
import org.arpha.dto.product.request.CreateProductRequest.ProductFileRequest;
import org.arpha.dto.product.request.UpdateProductRequest;
import org.arpha.dto.product.response.CreateProductResponse;
import org.arpha.dto.product.response.ProductResponse;
import org.arpha.dto.product.response.ProductSearchResponse;
import org.arpha.dto.product.response.RecommendationReason;
import org.arpha.dto.product.response.RecommendedProductResponse;
import org.arpha.entity.Category;
import org.arpha.entity.Genre;
import org.arpha.entity.Product;
import org.arpha.entity.ProductType;
import org.arpha.exception.CreateEntityException;
import org.arpha.exception.ProductNotFoundException;
import org.arpha.exception.UpdateEntityException;
import org.arpha.mapper.ProductMapper;
import org.arpha.mapper.helper.ProductMapperHelper;
import org.arpha.repository.CategoryRepository;
import org.arpha.repository.GenreRepository;
import org.arpha.repository.ProductRepository;
import org.arpha.repository.ProductTypeRepository;
import org.arpha.utils.Boxed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
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
    private final ProductTypeRepository productTypeRepository;
    private final CategoryRepository categoryRepository;
    private final GenreRepository genreRepository;

    @Override
    public CreateProductResponse createProduct(CreateProductRequest createProductRequest) { // Changed return type
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
        Page<Product> productPage = productRepository.findAll(predicate, pageable);
        List<Long> productIds = productPage.getContent().stream().map(Product::getId).collect(Collectors.toList());

        if (productIds.isEmpty()) {
            return Page.empty(pageable);
        }

        // 1. Fetch all media links in just two efficient batch calls
        Map<Long, List<String>> mainImageLinks = mediaService.getFileLinksForProducts(productIds, TargetType.PRODUCT_MAIN_IMG);
        Map<Long, List<String>> otherImageLinks = mediaService.getFileLinksForProducts(productIds, TargetType.PRODUCT);

        // 2. Create the context object to pass to the mappera
        ProductMapper.ImageLinksContext imageLinksContext = new ProductMapper.ImageLinksContext(mainImageLinks, otherImageLinks);

        // 3. Use the new optimized mapper to map the page of products
        return productPage.map(product -> productMapper.toProductResponse(product, imageLinksContext));
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
    @Transactional(readOnly = true)
    public List<ProductSearchResponse> searchProducts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String formattedQuery = Arrays.stream(query.trim().split("\\s+"))
                .map(word -> word + ":*")
                .collect(Collectors.joining(" & "));

        List<Product> products = productRepository.searchByQuery(formattedQuery);
        return products.stream()
                .map(productMapper::toProductSearchResponse)
                .collect(Collectors.toList());
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

            if (mostViewedProducts.isEmpty()) {
                // Fallback for cold start: return most recently added products
                Page<Product> recentProducts = productRepository.findAll(
                        Expressions.asBoolean(true).isTrue(),
                        PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"))
                );
                return recentProducts.stream()
                        .map(productMapper::toProductResponse)
                        .map(product -> new RecommendedProductResponse(product, RecommendationReason.BASED_ON_GENRE))
                        .toList();
            }

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

    private CreateProductResponse saveProduct(CreateProductRequest createProductRequest) { // Changed return type
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

        return new CreateProductResponse(product.getId(), fileResponses);
    }

    @Override
    public void generateFakeProducts(int count) {
        Faker faker = new Faker(new Locale("uk"));

        // Ensure at least one ProductType, Category, and Genre exists
        List<ProductType> productTypes = ensureProductTypesExist();
        List<Category> categories = ensureCategoriesExist();
        List<Genre> genres = ensureGenresExist();

        for (int i = 0; i < count; i++) {
            CreateProductRequest request = new CreateProductRequest();

            request.setName(faker.commerce().productName() + " " + faker.superhero().name());
            request.setDescription(faker.lorem().paragraphs(3).toString());
            request.setPrice(new BigDecimal(faker.commerce().price(50, 2000)));
            request.setQuantity(faker.number().numberBetween(0, 100));

            // Assign a random existing product type
            request.setProductTypeId(productTypes.get(ThreadLocalRandom.current().nextInt(productTypes.size())).getId());

            // Assign random categories and genres
            request.setCategories(Collections.singleton(categories.get(ThreadLocalRandom.current().nextInt(categories.size())).getName()));
            request.setGenres(Collections.singleton(genres.get(ThreadLocalRandom.current().nextInt(genres.size())).getName()));

            // Game details
            request.setMinPlayerNumber(faker.number().numberBetween(1, 2));
            request.setMaxPlayerNumber(faker.number().numberBetween(3, 8));
            request.setMinPlayTime(faker.number().numberBetween(15, 30));
            request.setMaxPlayTime(faker.number().numberBetween(45, 120));
            request.setMinAge(faker.number().numberBetween(6, 18));
            request.setAuthor(faker.book().author());
            request.setPublisher(faker.book().publisher());
            request.setBggRating(faker.number().randomDouble(2, 6, 10));

            request.setLength(new BigDecimal(faker.number().randomDouble(2, 10, 50)));
            request.setHeight(new BigDecimal(faker.number().randomDouble(2, 5, 20)));
            request.setWidth(new BigDecimal(faker.number().randomDouble(2, 10, 50)));
            request.setWeight(new BigDecimal(faker.number().randomDouble(3, 1, 5)));


            try {
                createProduct(request);
            } catch (CreateEntityException e) {
                // Ignore if a product with the same name already exists and continue
                System.out.println("Skipping duplicate product: " + request.getName());
            }
        }
    }

    private List<ProductType> ensureProductTypesExist() {
        List<ProductType> types = productTypeRepository.findAll();
        if (types.isEmpty()) {
            ProductType defaultType = new ProductType();
            defaultType.setName("Standard Game");
            defaultType.setDimension(new Dimension(BigDecimal.valueOf(20), BigDecimal.valueOf(0.5), BigDecimal.valueOf(30), BigDecimal.valueOf(10)));
            types.add(productTypeRepository.save(defaultType));
        }
        return types;
    }

    private List<Category> ensureCategoriesExist() {
        List<Category> cats = categoryRepository.findAll();
        if (cats.isEmpty()) {
            List<String> defaultCategories = List.of("Сімейна", "Для вечірок", "Стратегія", "Кооперативна");
            defaultCategories.forEach(name -> {
                Category c = new Category();
                c.setName(name);
                cats.add(categoryRepository.save(c));
            });
        }
        return cats;
    }

    private List<Genre> ensureGenresExist() {
        List<Genre> gens = genreRepository.findAll();
        if (gens.isEmpty()) {
            List<String> defaultGenres = List.of("Фентезі", "Наукова фантастика", "Детектив", "Пригоди");
            defaultGenres.forEach(name -> {
                Genre g = new Genre();
                g.setName(name);
                gens.add(genreRepository.save(g));
            });
        }
        return gens;
    }

}
