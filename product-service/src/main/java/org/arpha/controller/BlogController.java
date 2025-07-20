// tabletop-games-shop/product-service/src/main/java/org/arpha/controller/BlogController.java

package org.arpha.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.blog.request.CreateCommentRequest;
import org.arpha.dto.blog.request.CreatePostRequest;
import org.arpha.dto.blog.response.CommentResponse;
import org.arpha.dto.blog.response.PostResponse;
import org.arpha.dto.user.response.UserResponse;
import org.arpha.entity.Post;
import org.arpha.service.BlogService;
import org.arpha.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class BlogController {

    private final BlogService blogService;
    private final UserService userServiceClient;

    @PostMapping("/posts")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest request) {
        return new ResponseEntity<>(blogService.createPost(request), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getPostById(id));
    }

    @GetMapping("/posts")
    public Page<PostResponse> getAllPosts(@QuerydslPredicate(root = Post.class) Predicate predicate, Pageable pageable) {
        return blogService.getAllPosts(predicate, pageable);
    }

    @DeleteMapping("/posts/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        blogService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CreateCommentRequest request,  @AuthenticationPrincipal UserDetails userDetails) {
        UserResponse currentUser = userServiceClient.findUserByEmail(userDetails.getUsername());
        CommentResponse response = blogService.createComment(request, currentUser.getId(), currentUser.getFirstName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        UserResponse currentUser = userServiceClient.findUserByEmail(userDetails.getUsername());
        blogService.deleteComment(id, currentUser.getId(), currentUser.getRole());
        return ResponseEntity.noContent().build();
    }
}