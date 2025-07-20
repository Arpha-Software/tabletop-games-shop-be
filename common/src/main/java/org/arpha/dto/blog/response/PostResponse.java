// tabletop-games-shop/common/src/main/java/org/arpha/dto/blog/response/PostResponse.java

package org.arpha.dto.blog.response;

import lombok.Data;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String author;

    private String mainImageUrl;
    private List<String> otherImageUrls;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<CommentResponse> comments;
}