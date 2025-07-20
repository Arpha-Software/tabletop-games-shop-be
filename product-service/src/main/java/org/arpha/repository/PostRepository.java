// tabletop-games-shop/product-service/src/main/java/org/arpha/repository/PostRepository.java

package org.arpha.repository;

import org.arpha.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PostRepository extends JpaRepository<Post, Long>, QuerydslPredicateExecutor<Post> {}