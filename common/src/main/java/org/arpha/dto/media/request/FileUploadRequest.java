package org.arpha.dto.media.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.media.enums.UploadType;
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
    private UploadType targetType;

}
