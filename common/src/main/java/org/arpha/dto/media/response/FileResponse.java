package org.arpha.dto.media.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.media.FileAccessLink;
import org.arpha.dto.media.enums.TargetType;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class FileResponse {

    private long id;
    private String fileName;
    private long fileSize;
    private long targetId;
    private TargetType targetType;
    private FileAccessLink fileAccessLink;

    private String fileUuid;

}
