package org.arpha.dto.media;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.media.enums.AccessType;
import org.arpha.misc.MimeTypeSerializer;
import org.springframework.util.MimeType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileAccessLink {

    private String link;
    private AccessType accessType;

    @JsonSerialize(using = MimeTypeSerializer.class)
    private MimeType fileType;

}
