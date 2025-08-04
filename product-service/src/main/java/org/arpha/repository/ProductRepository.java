package org.arpha.repository;

import org.arpha.dto.product.response.PriceRange;
import org.arpha.entity.Product;
import org.arpha.entity.QProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product>, QuerydslPredicateExecutor<Product> {

    boolean existsById(long id);

    boolean existsByName(String name);

    @Query(value = "SELECT p FROM Product p LEFT JOIN FETCH p.addons WHERE p.id = :id")
    Optional<Product> findByIdWithAddons(Long id);

    @Query("SELECT COUNT(p) FROM Product p JOIN p.genres c WHERE c.id = :genreId")
    long containsGenreCount(long genreId);

    @Query("SELECT COUNT(p) FROM Product p JOIN p.categories c WHERE c.id = :categoryId")
    long containsCategoryCount(long categoryId);

    @Query("SELECT DISTINCT p.language FROM Product p WHERE p.language IS NOT NULL AND p.language <> ''")
    Set<String> findDistinctLanguages();

    @Query("SELECT DISTINCT p.publisher FROM Product p WHERE p.publisher IS NOT NULL AND p.publisher <> ''")
    Set<String> findDistinctPublishers();

    @Query("SELECT DISTINCT m FROM Product p JOIN p.mechanics m")
    Set<String> findDistinctMechanics();

    @Query("SELECT new org.arpha.dto.product.response.PriceRange(MIN(p.price), MAX(p.price)) FROM Product p")
    PriceRange findPriceRange();

    @Query(value = "SELECT p.* FROM products p " +
            "WHERE p.search_vector_en @@ to_tsquery('english', :query) OR " +
            "      p.search_vector_uk @@ to_tsquery('simple', :query) " +
            "ORDER BY ts_rank(p.search_vector_en, to_tsquery('english', :query)) + " +
            "         ts_rank(p.search_vector_uk, to_tsquery('simple', :query)) DESC " +
            "LIMIT 10",
            nativeQuery = true)
    List<Product> searchByQuery(@Param("query") String query);

}