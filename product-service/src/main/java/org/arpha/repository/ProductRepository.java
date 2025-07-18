package org.arpha.repository;

import org.arpha.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

    boolean existsById(long id);
    boolean existsByName(String name);

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
