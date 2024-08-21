package org.arpha.mapper;

import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.media.response.FileResponse;
import org.arpha.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "file.originalFilename")
    @Mapping(target = "type", source = "file.contentType")
    @Mapping(target = "size", source = "file.size")
    @Mapping(target = "targetType", source = "fileUploadRequest.targetType")
    @Mapping(target = "targetId", source = "fileUploadRequest.targetId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    File toFile(MultipartFile file, FileUploadRequest fileUploadRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fileName", source = "file.name")
    @Mapping(target = "fileType", source = "file.type")
    @Mapping(target = "size", source = "file.size")
    @Mapping(target = "targetType", source = "file.targetType")
    FileResponse toFileResponse(File file);

}
