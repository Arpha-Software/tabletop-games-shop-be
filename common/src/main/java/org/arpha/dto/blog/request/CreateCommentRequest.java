// tabletop-games-shop/common/src/main/java/org/arpha/dto/blog/request/CreateCommentRequest.java

package org.arpha.dto.blog.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentRequest {
    @NotNull
    private Long postId;

    @NotBlank
    private String content;
}