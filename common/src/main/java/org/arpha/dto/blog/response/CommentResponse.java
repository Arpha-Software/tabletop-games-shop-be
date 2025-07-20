// tabletop-games-shop/common/src/main/java/org/arpha/dto/blog/response/CommentResponse.java

package org.arpha.dto.blog.response;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class CommentResponse {
    private Long id;
    private String content;
    private String username;
    private Long userId;
    private OffsetDateTime createdAt;
}