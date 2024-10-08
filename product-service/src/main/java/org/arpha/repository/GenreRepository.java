package org.arpha.repository;

import org.arpha.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GenreRepository extends JpaRepository<Genre, Long>, QuerydslPredicateExecutor<Genre> {

    boolean existsByName(String name);
    Genre findByName(String name);
    
}
