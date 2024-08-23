package org.arpha.dto.media.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.media.FileAccessLink;
import org.arpha.dto.media.enums.UploadType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {

    private long id;
    private String fileName;
    private long fileSize;
    private long targetId;
    private UploadType targetType;
    private FileAccessLink fileAccessLink;

}
