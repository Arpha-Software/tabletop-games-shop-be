package org.arpha.repository;

import com.querydsl.core.types.Predicate;
import org.arpha.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

    boolean existsById(long id);
    boolean existsByName(String name);

    // OPTIMIZATION: This query fetches products and their associated addons in a single query
    // using a LEFT JOIN FETCH. This is an alternative to @BatchSize and is very efficient
    // for scenarios where you always want the addons.
    @Query(value = "SELECT p FROM Product p LEFT JOIN FETCH p.addons WHERE p.id = :id")
    Optional<Product> findByIdWithAddons(Long id);

    // OPTIMIZATION: Using an EntityGraph is another powerful way to solve the N+1 problem.
    // It tells JPA to fetch the specified associations eagerly.
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
}
