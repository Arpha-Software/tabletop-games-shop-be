package org.arpha.dto.media.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.media.enums.UploadType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {

    private long id;
    private String fileName;
    private long size;
    private long targetId;
    private String fileType;
    private UploadType targetType;
    private String link;

}
