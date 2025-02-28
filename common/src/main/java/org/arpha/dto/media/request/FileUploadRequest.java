package org.arpha.dto.media.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.media.enums.TargetType;
import org.arpha.misc.MimeTypeDeserializer;
import org.springframework.util.MimeType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadRequest {

    @NotNull
    private MimeType type;
    @Min(1)
    private long fileSize;
    @Min(1)
    private long targetId;

    @NotNull
    @Schema(type = "string", example = "application/json")
    @JsonDeserialize(using = MimeTypeDeserializer.class)
    private TargetType targetType;

}
