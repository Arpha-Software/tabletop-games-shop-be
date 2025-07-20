// tabletop-games-shop/abstract-product-service/src/main/java/org/arpha/service/BlogService.java

package org.arpha.service;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.blog.request.CreateCommentRequest;
import org.arpha.dto.blog.request.CreatePostRequest;
import org.arpha.dto.blog.response.CommentResponse;
import org.arpha.dto.blog.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    PostResponse createPost(CreatePostRequest request);
    void deletePost(Long postId);
    PostResponse getPostById(Long postId);
    Page<PostResponse> getAllPosts(Predicate predicate, Pageable pageable);
    CommentResponse createComment(CreateCommentRequest request, Long userId, String username);
    void deleteComment(Long commentId, Long userId, String userRole);
}