package org.arpha.dto.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.media.enums.AccessType;
import org.springframework.util.MimeType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileAccessLink {

    private String link;
    private AccessType accessType;
    private MimeType fileType;

}
