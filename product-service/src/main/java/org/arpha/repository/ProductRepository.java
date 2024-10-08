package org.arpha.repository;

import org.arpha.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

    boolean existsById(long id);
    boolean existsByName(String name);

    @Query("SELECT COUNT(p) FROM Product p JOIN p.genres c WHERE c.id = :genreId")
    long containsGenreCount(long genreId);
    @Query("SELECT COUNT(p) FROM Product p JOIN p.categories c WHERE c.id = :categoryId")
    long containsCategoryCount(long categoryId);
}
