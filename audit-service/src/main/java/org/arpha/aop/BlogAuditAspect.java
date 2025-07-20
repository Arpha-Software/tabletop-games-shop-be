// tabletop-games-shop/audit-service/src/main/java/org/arpha/aop/BlogAuditAspect.java

package org.arpha.aop;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.audit.TargetType;
import org.arpha.dto.blog.response.CommentResponse;
import org.arpha.dto.blog.response.PostResponse;
import org.arpha.service.AuditService;
import org.arpha.utills.AspectUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static org.arpha.dto.audit.Action.*;

@Aspect
@Component
@RequiredArgsConstructor
public class BlogAuditAspect {

    private final AuditService auditService;

    @AfterReturning(value = "execution(* org.arpha.service.BlogService.createPost(..))", returning = "response")
    public void createPostAdvice(PostResponse response) {
        auditService.saveAudit(CREATE_POST, response.getId(), AspectUtils.getAuthenticatedUserId(), TargetType.POST);
    }

    @AfterReturning("execution(* org.arpha.service.BlogService.deletePost(..)) && args(postId)")
    public void deletePostAdvice(long postId) {
        auditService.saveAudit(DELETE_POST, postId, AspectUtils.getAuthenticatedUserId(), TargetType.POST);
    }

    @AfterReturning(value = "execution(* org.arpha.service.BlogService.getPostById(..))", returning = "response")
    public void findPostByIdAdvice(PostResponse response) {
        auditService.saveAudit(FIND_POST_BY_ID, response.getId(), AspectUtils.getAuthenticatedUserId(), TargetType.POST);
    }

    @AfterReturning(value = "execution(* org.arpha.service.BlogService.createComment(..))", returning = "response")
    public void createCommentAdvice(CommentResponse response) {
        auditService.saveAudit(CREATE_COMMENT, response.getId(), AspectUtils.getAuthenticatedUserId(), TargetType.COMMENT);
    }

    @AfterReturning("execution(* org.arpha.service.BlogService.deleteComment(..)) && args(commentId, ..)")
    public void deleteCommentAdvice(long commentId) {
        auditService.saveAudit(DELETE_COMMENT, commentId, AspectUtils.getAuthenticatedUserId(), TargetType.COMMENT);
    }
}