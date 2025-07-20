// tabletop-games-shop/common/src/main/java/org/arpha/dto/blog/request/CreatePostRequest.java

package org.arpha.dto.blog.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.arpha.misc.MimeTypeDeserializer;
import org.springframework.util.MimeType;

import java.util.List;

@Data
public class CreatePostRequest {
    @NotBlank
    @Size(min = 5, max = 255)
    private String title;

    @NotBlank
    private String content; // Plain text as requested

    private List<PostFileRequest> images;

    @Data
    public static class PostFileRequest {
        @NotNull
        @Schema(type = "string", example = "image/jpeg")
        @JsonDeserialize(using = MimeTypeDeserializer.class)
        private MimeType type;

        @Min(1)
        private long fileSize;
        private String uuid;
        private Boolean isMain;
    }
}
