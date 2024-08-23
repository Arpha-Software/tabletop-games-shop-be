package org.arpha.dto.media.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.media.FileAccessLink;
import org.arpha.dto.media.enums.TargetType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {

    private long id;
    private String fileName;
    private long fileSize;
    private long targetId;
    private TargetType targetType;
    private FileAccessLink fileAccessLink;

}
