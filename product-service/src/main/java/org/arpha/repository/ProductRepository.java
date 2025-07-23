package org.arpha.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
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
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
        bindings.excludeUnlistedProperties(true);

        // --- FIELD BINDINGS ---
        bindString(bindings, product.name);
        bindString(bindings, product.description);
        bindString(bindings, product.language);
        bindString(bindings, product.publisher);
        bindString(bindings, product.author);
        bindString(bindings, product.components);
        bindString(bindings, product.rulesLink);

        bindNumber(bindings, product.id);
        bindNumber(bindings, product.quantity);
        bindNumber(bindings, product.price);
        bindNumber(bindings, product.minPlayerNumber);
        bindNumber(bindings, product.maxPlayerNumber);
        bindNumber(bindings, product.minPlayTime);
        bindNumber(bindings, product.maxPlayTime);
        bindNumber(bindings, product.minAge);
        bindNumber(bindings, product.bggRating);
        bindNumber(bindings, product.complexity);
        bindNumber(bindings, product.averageRating);
        bindNumber(bindings, product.reviewCount);

        bindDateTime(bindings, product.createdAt);
        bindDateTime(bindings, product.updatedAt);

        // --- EMBEDDED & RELATIONAL BINDINGS ---
        bindNumber(bindings, product.dimension.width);
        bindNumber(bindings, product.dimension.weight);
        bindNumber(bindings, product.dimension.length);
        bindNumber(bindings, product.dimension.height);

        bindNumber(bindings, product.type.id);
        bindString(bindings, product.type.name);

        // --- COLLECTION BINDINGS ---
        bindings.bind(product.mechanics).as("mechanics.in").all((path, values) -> {
            BooleanBuilder predicate = new BooleanBuilder();
            values.stream().flatMap(Collection::stream).forEach(value -> predicate.or(path.contains(value)));
            return predicate.hasValue() ? Optional.of(predicate) : Optional.empty();
        });
        bindings.bind(product.mechanics).as("mechanics.all").all((path, values) -> {
            BooleanBuilder predicate = new BooleanBuilder();
            values.stream().flatMap(Collection::stream).forEach(value -> predicate.and(path.contains(value)));
            return predicate.hasValue() ? Optional.of(predicate) : Optional.empty();
        });

        bindCollectionById(bindings, product.categories.any().id, "categories.id.in");
        bindCollectionByName(bindings, product.categories.any().name, "categories.name.in");

        bindCollectionById(bindings, product.genres.any().id, "genres.id.in");
        bindCollectionByName(bindings, product.genres.any().name, "genres.name.in");

        bindCollectionById(bindings, product.addons.any().id, "addons.id.in");
        bindCollectionByName(bindings, product.addons.any().name, "addons.name.in");
    }

    /**
     * Helper method to bind common String operators.
     */
    private static void bindString(QuerydslBindings bindings, StringPath path) {
        String name = path.getMetadata().getName();
        // Single value operators use .first()
        bindings.bind(path).as(name + ".eq").first((p, v) -> p.equalsIgnoreCase(v));
        bindings.bind(path).as(name + ".ne").first((p, v) -> p.notEqualsIgnoreCase(v));
        bindings.bind(path).as(name + ".contains").first((p, v) -> p.containsIgnoreCase(v));

        // Multi-value operators use .all()
        bindings.bind(path).as(name + ".in").all((p, v) -> {
            BooleanBuilder builder = new BooleanBuilder();
            v.forEach(val -> builder.or(p.equalsIgnoreCase(val)));
            return Optional.of(builder);
        });
    }

    /**
     * Helper method to bind common Number operators for standard number types.
     */
    private static <T extends Number & Comparable<?>> void bindNumber(QuerydslBindings bindings, NumberPath<T> path) {
        String name = path.getMetadata().getName();
        // Single value operators use .first()
        bindings.bind(path).as(name + ".eq").first((p, v) -> p.eq(v));
        bindings.bind(path).as(name + ".ne").first((p, v) -> p.ne(v));
        bindings.bind(path).as(name + ".gt").first((p, v) -> p.gt(v));
        bindings.bind(path).as(name + ".gte").first((p, v) -> p.goe(v));
        bindings.bind(path).as(name + ".lt").first((p, v) -> p.lt(v));
        bindings.bind(path).as(name + ".lte").first((p, v) -> p.loe(v));

        // Multi-value operators use .all()
        bindings.bind(path).as(name + ".in").all((p, v) -> Optional.of(p.in(v)));
        bindings.bind(path).as(name + ".between").all((p, values) -> {
            if (values.size() != 2) {
                // Ignore if not exactly two values are provided
                return Optional.empty();
            }
            Iterator<? extends T> it = values.iterator();
            return Optional.of(p.between(it.next(), it.next()));
        });
    }

    /**
     * Overloaded helper method to bind common Number operators for BigDecimal.
     */
    private static void bindNumber(QuerydslBindings bindings, ComparablePath<BigDecimal> path) {
        String name = path.getMetadata().getName();
        // Single value operators use .first()
        bindings.bind(path).as(name + ".eq").first((p, v) -> p.eq(v));
        bindings.bind(path).as(name + ".ne").first((p, v) -> p.ne(v));
        bindings.bind(path).as(name + ".gt").first((p, v) -> p.gt(v));
        bindings.bind(path).as(name + ".gte").first((p, v) -> p.goe(v));
        bindings.bind(path).as(name + ".lt").first((p, v) -> p.lt(v));
        bindings.bind(path).as(name + ".lte").first((p, v) -> p.loe(v));

        // Multi-value operators use .all()
        bindings.bind(path).as(name + ".in").all((p, v) -> Optional.of(p.in(v)));
        bindings.bind(path).as(name + ".between").all((p, values) -> {
            if (values.size() != 2) {
                return Optional.empty();
            }
            Iterator<? extends BigDecimal> it = values.iterator();
            return Optional.of(p.between(it.next(), it.next()));
        });
    }

    /**
     * Helper method to bind common DateTime operators.
     */
    private static <T extends Comparable> void bindDateTime(QuerydslBindings bindings, DateTimePath<T> path) {
        String name = path.getMetadata().getName();
        // Single value operators use .first()
        bindings.bind(path).as(name + ".eq").first((p, v) -> p.eq(v));
        bindings.bind(path).as(name + ".ne").first((p, v) -> p.ne(v));
        bindings.bind(path).as(name + ".gt").first((p, v) -> p.gt(v));
        bindings.bind(path).as(name + ".gte").first((p, v) -> p.goe(v));
        bindings.bind(path).as(name + ".lt").first((p, v) -> p.lt(v));
        bindings.bind(path).as(name + ".lte").first((p, v) -> p.loe(v));

        bindings.bind(path).as(name + ".between").all((p, values) -> {
            if (values.size() != 2) {
                return Optional.empty();
            }
            Iterator<? extends T> it = values.iterator();
            return Optional.of(p.between(it.next(), it.next()));
        });
    }

    /**
     * Helper method to bind a collection by the related entity's ID.
     */
    private static void bindCollectionById(QuerydslBindings bindings, NumberPath<Long> path, String alias) {
        bindings.bind(path).as(alias).all((p, values) -> {
            BooleanBuilder builder = new BooleanBuilder();
            values.forEach(val -> builder.or(p.eq(val)));
            return builder.hasValue() ? Optional.of(builder) : Optional.empty();
        });
    }

    /**
     * Helper method to bind a collection by the related entity's name.
     */
    private static void bindCollectionByName(QuerydslBindings bindings, StringPath path, String alias) {
        bindings.bind(path).as(alias).all((p, values) -> {
            BooleanBuilder builder = new BooleanBuilder();
            values.stream().forEach(val -> builder.or(p.equalsIgnoreCase(val)));
            return builder.hasValue() ? Optional.of(builder) : Optional.empty();
        });
    }
}