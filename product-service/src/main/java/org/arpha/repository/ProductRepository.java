package org.arpha.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SetPath;
import com.querydsl.core.types.dsl.StringPath;
import org.arpha.entity.Product;
import org.arpha.entity.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product>, QuerydslBinderCustomizer<QProduct> {

    boolean existsById(long id);
    boolean existsByName(String name);

    @Query(value = "SELECT p FROM Product p LEFT JOIN FETCH p.addons WHERE p.id = :id")
    Optional<Product> findByIdWithAddons(Long id);

    @Override
    @EntityGraph(attributePaths = {"addons", "categories", "genres", "type"})
    Page<Product> findAll(Predicate predicate, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Product p JOIN p.genres c WHERE c.id = :genreId")
    long containsGenreCount(long genreId);
    @Query("SELECT COUNT(p) FROM Product p JOIN p.categories c WHERE c.id = :categoryId")
    long containsCategoryCount(long categoryId);

    @Query(value = "SELECT p.* FROM products p " +
            "WHERE p.search_vector_en @@ to_tsquery('english', :query) OR " +
            "      p.search_vector_uk @@ to_tsquery('simple', :query) " +
            "ORDER BY ts_rank(p.search_vector_en, to_tsquery('english', :query)) + " +
            "         ts_rank(p.search_vector_uk, to_tsquery('simple', :query)) DESC " +
            "LIMIT 10",
            nativeQuery = true)
    List<Product> searchByQuery(@Param("query") String query);
    @Override
    default void customize(QuerydslBindings bindings, QProduct product) {
        // Default exclusion of all properties to enable explicit binding
        bindings.excludeUnlistedProperties(true);

        // Exclude specific complex properties from default auto-binding
        bindings.excluding(product.categories);
        bindings.excluding(product.genres);
        bindings.excluding(product.type);
        bindings.excluding(product.addons);
        bindings.excluding(product.dimension);
        // Removed `product.mechanics` from `excluding` here, as we're binding it directly by its path
        // but `bindings.excludeUnlistedProperties(true)` handles it implicitly if not bound.

        // --- Basic Field Bindings (Direct Properties) ---

        // String fields: case-insensitive contains for search-like behavior
        // This general binding will apply to `name`, `description`, `publisher`, `author`, `components`, `rulesLink`
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringPath::containsIgnoreCase);

        // Specific String fields where exact match is more appropriate, or custom logic
        // This will override the general String.class binding for `product.language`
        bindings.bind(product.language).first(StringPath::eq);

        // Numeric fields: default to exact match
        bindings.bind(product.id).first(NumberPath::eq);
        bindings.bind(product.quantity).first(NumberPath::eq);
        bindings.bind(product.minPlayerNumber).first(NumberPath::eq);
        bindings.bind(product.maxPlayerNumber).first(NumberPath::eq);
        bindings.bind(product.minPlayTime).first(NumberPath::eq);
        bindings.bind(product.maxPlayTime).first(NumberPath::eq);
        bindings.bind(product.minAge).first(NumberPath::eq);
        bindings.bind(product.bggRating).first(NumberPath::eq);
        bindings.bind(product.complexity).first(NumberPath::eq);
        bindings.bind(product.averageRating).first(NumberPath::eq);
        bindings.bind(product.reviewCount).first(NumberPath::eq);


        // Date/Time fields: exact match by default
        bindings.bind(product.createdAt).first(DateTimePath::eq);
        bindings.bind(product.updatedAt).first(DateTimePath::eq);


        // --- Nested Object Bindings ---

        // ProductType: Filter by type name or ID
        bindings.bind(product.type.id).first(NumberPath::eq);
        bindings.bind(product.type.name).first(StringPath::containsIgnoreCase);

        // Dimension (Embedded): Filter by individual dimension properties
        bindings.bind(product.dimension.width).first(NumberPath::eq);
        bindings.bind(product.dimension.weight).first(NumberPath::eq);
        bindings.bind(product.dimension.length).first(NumberPath::eq);
        bindings.bind(product.dimension.height).first(NumberPath::eq);


        // --- Collection Bindings (Multiple values with OR logic) ---

        // Categories: Filter by category name (case-insensitive contains)
        // Example: ?categories.name=Strategy%20Games&categories.name=Card%20Games
        bindings.bind(product.categories.any().name).all((StringPath path, Collection<? extends String> values) -> {
            BooleanBuilder builder = new BooleanBuilder();
            for (String value : values) {
                builder.or(path.containsIgnoreCase(value));
            }
            return Optional.of(builder);
        });
        // Categories: Filter by category ID
        // Example: ?categories.id=1&categories.id=5
        bindings.bind(product.categories.any().id).all((NumberPath<Long> path, Collection<? extends Long> values) -> {
            BooleanBuilder builder = new BooleanBuilder();
            for (Long value : values) {
                builder.or(path.eq(value));
            }
            return Optional.of(builder);
        });


        // Genres: Filter by genre name (case-insensitive contains)
        // Example: ?genres.name=Fantasy&genres.name=Sci-Fi
        bindings.bind(product.genres.any().name).all((StringPath path, Collection<? extends String> values) -> {
            BooleanBuilder builder = new BooleanBuilder();
            for (String value : values) {
                builder.or(path.containsIgnoreCase(value));
            }
            return Optional.of(builder);
        });
        // Genres: Filter by genre ID
        // Example: ?genres.id=2&genres.id=7
        bindings.bind(product.genres.any().id).all((NumberPath<Long> path, Collection<? extends Long> values) -> {
            BooleanBuilder builder = new BooleanBuilder();
            for (Long value : values) {
                builder.or(path.eq(value));
            }
            return Optional.of(builder);
        });


        // Addons: Filter by addon product name (case-insensitive contains)
        // Example: ?addons.name=Expansion%20Pack&addons.name=Base%20Game
        bindings.bind(product.addons.any().name).all((StringPath path, Collection<? extends String> values) -> {
            BooleanBuilder builder = new BooleanBuilder();
            for (String value : values) {
                builder.or(path.containsIgnoreCase(value));
            }
            return Optional.of(builder);
        });
        // Addons: Filter by addon product ID
        // Example: ?addons.id=100&addons.id=101
        bindings.bind(product.addons.any().id).all((NumberPath<Long> path, Collection<? extends Long> values) -> {
            BooleanBuilder builder = new BooleanBuilder();
            for (Long value : values) {
                builder.or(path.eq(value));
            }
            return Optional.of(builder);
        });

        // Mechanics (ElementCollection of Strings): Filter by specific mechanic (exact match for each)
        // Example: ?mechanics=Deckbuilding&mechanics=Worker%20Placement
        // This will look for products where the 'mechanics' set contains ANY of the provided values (OR logic)
        bindings.bind(product.mechanics).all((SetPath<String, StringPath> path, Collection<? extends Set<String>> values) -> {
            BooleanBuilder builder = new BooleanBuilder();
            for (Set<String> value : values) {
                for (String key : value) {
                    builder.or(path.contains(key));
                }
            }
            return Optional.of(builder);
        });
    }
}
