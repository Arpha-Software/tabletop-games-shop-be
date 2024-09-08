package org.arpha.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.FileAccessLink;
import org.arpha.dto.media.enums.AccessType;
import org.arpha.entity.File;
import org.mapstruct.Context;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileMapperHelper {

    @Named("generateAccessLink")
    public FileAccessLink generateAccessLink(File file, @Context AccessType accessType, @Context String link) {
        return new FileAccessLink(link, accessType, file.getType());
    }

}
