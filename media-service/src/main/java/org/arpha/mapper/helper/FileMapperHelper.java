package org.arpha.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.FileAccessLink;
import org.arpha.dto.media.enums.AccessType;
import org.arpha.entity.File;
import org.arpha.service.BlobService;
import org.mapstruct.Context;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileMapperHelper {

    private final BlobService blobService;

    @Named("generateAccessLink")
    public FileAccessLink generateAccessLink(File file, @Context AccessType accessType) {
        return new FileAccessLink(blobService.generateLink(file.getName(), accessType), accessType, file.getType());
    }

}
