// tabletop-games-shop/product-service/src/main/java/org/arpha/repository/CommentRepository.java

package org.arpha.repository;

import org.arpha.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {}