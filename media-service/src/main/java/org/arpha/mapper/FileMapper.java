package org.arpha.mapper;

import com.azure.storage.blob.BlobClient;
import org.arpha.dto.media.enums.AccessType;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.media.response.FileResponse;
import org.arpha.entity.File;
import org.arpha.mapper.helper.FileMapperHelper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Mapper(componentModel = "spring", uses = {FileMapperHelper.class})
public interface FileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "fileName")
    @Mapping(target = "type", source = "fileUploadRequest.type")
    @Mapping(target = "fileSize", source = "fileUploadRequest.fileSize")
    @Mapping(target = "targetType", source = "fileUploadRequest.targetType")
    @Mapping(target = "targetId", source = "fileUploadRequest.targetId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    File toFile(FileUploadRequest fileUploadRequest, String fileName);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fileName", source = "file.name")
    @Mapping(target = "fileAccessLink", source = "file", qualifiedByName = "generateAccessLink")
    @Mapping(target = "fileSize", source = "file.fileSize")
    @Mapping(target = "targetType", source = "file.targetType")
    FileResponse toFileResponse(File file, @Context AccessType accessType, @Context String link);

}
