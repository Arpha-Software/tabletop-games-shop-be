package org.arpha.dto.media.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.media.enums.UploadType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadRequest {

    private long targetId;
    private UploadType targetType;

}
