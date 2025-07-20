// tabletop-games-shop/product-service/src/main/java/org/arpha/service/BlogServiceImpl.java

package org.arpha.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.blog.request.CreateCommentRequest;
import org.arpha.dto.blog.request.CreatePostRequest;
import org.arpha.dto.blog.response.CommentResponse;
import org.arpha.dto.blog.response.PostResponse;
import org.arpha.dto.media.enums.TargetType;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.media.response.FileResponse;
import org.arpha.entity.Comment;
import org.arpha.entity.Post;
import org.arpha.exception.CreateEntityException;
import org.arpha.exception.ProductNotFoundException;
import org.arpha.mapper.CommentMapper;
import org.arpha.mapper.PostMapper;
import org.arpha.repository.CommentRepository;
import org.arpha.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogServiceImpl implements BlogService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final MediaService mediaService;

    @Override
    public PostResponse createPost(CreatePostRequest request) {
        Post post = postMapper.toPost(request);
        Post savedPost = postRepository.save(post);

        if (!CollectionUtils.isEmpty(request.getImages())) {
            // Find the main image request, if it exists
            Optional<CreatePostRequest.PostFileRequest> mainImageRequest = request.getImages().stream()
                    .filter(img -> img.getIsMain() != null && img.getIsMain())
                    .findFirst();

            // Upload the main image
            mainImageRequest.ifPresent(img -> {
                FileUploadRequest fileUploadRequest = new FileUploadRequest(
                        img.getType(), img.getFileSize(), savedPost.getId(),
                        TargetType.POST_MAIN_IMAGE, img.getUuid()
                );
                mediaService.upload(fileUploadRequest);
            });

            // Upload the rest of the images
            List<CreatePostRequest.PostFileRequest> otherImages = request.getImages().stream()
                    .filter(img -> img.getIsMain() == null || !img.getIsMain())
                    .collect(Collectors.toList());

            otherImages.forEach(img -> {
                FileUploadRequest fileUploadRequest = new FileUploadRequest(
                        img.getType(), img.getFileSize(), savedPost.getId(),
                        TargetType.POST_IMAGE, img.getUuid()
                );
                mediaService.upload(fileUploadRequest);
            });
        }

        return postMapper.toPostResponse(savedPost);
    }

    @Override
    public void deletePost(Long postId) {
        mediaService.deleteAllByTargetIdAndType(postId, TargetType.POST_MAIN_IMAGE);
        mediaService.deleteAllByTargetIdAndType(postId, TargetType.POST_IMAGE);
        postRepository.deleteById(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPostById(Long postId) {
        return postRepository.findById(postId)
                .map(postMapper::toPostResponse)
                .orElseThrow(() -> new ProductNotFoundException("Post not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(Predicate predicate, Pageable pageable) {
        return postRepository.findAll(predicate, pageable).map(postMapper::toPostResponse);
    }

    // ... (comment methods remain unchanged)
    @Override
    public CommentResponse createComment(CreateCommentRequest request, Long userId, String username) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new ProductNotFoundException("Post not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUserId(userId);
        comment.setUsername(username);
        comment.setContent(request.getContent());

        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long commentId, Long userId, String userRole) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ProductNotFoundException("Comment not found"));

        if (!comment.getUserId().equals(userId) && !"ROLE_ADMIN".equals(userRole)) {
            throw new AccessDeniedException("You do not have permission to delete this comment.");
        }

        commentRepository.delete(comment);
    }
}
