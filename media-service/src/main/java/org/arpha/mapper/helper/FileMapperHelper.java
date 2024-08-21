package org.arpha.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.enums.AccessType;
import org.arpha.service.helper.MediaServiceHelper;
import org.mapstruct.Context;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileMapperHelper {

    private final MediaServiceHelper mediaServiceHelper;

    @Named("generateAccessLink")
    public String generateAccessLink(String fileName, @Context AccessType accessType) {
        return mediaServiceHelper.generateLink(fileName, accessType);
    }

}
