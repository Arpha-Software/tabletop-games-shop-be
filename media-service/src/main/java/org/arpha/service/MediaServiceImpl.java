package org.arpha.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.enums.TargetType;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.media.response.FileResponse;
import org.arpha.entity.File;
import org.arpha.exception.FileNotFoundException;
import org.arpha.exception.FileUploadException;
import org.arpha.mapper.FileMapper;
import org.arpha.repository.FileRepository;
import org.arpha.utils.Boxed;
import org.arpha.validator.FileRequestValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import java.util.UUID;

import static org.arpha.dto.media.enums.AccessType.READ;
import static org.arpha.dto.media.enums.AccessType.WRITE;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final BlobContainerClient blobContainerClient;
    private final FileRequestValidator fileRequestValidator;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public FileResponse upload(FileUploadRequest fileUploadRequest) {
        return Boxed
                .of(fileUploadRequest)
                .mapToBoxed(this::generateFileName)
                .mapToBoxed(blobContainerClient::getBlobClient)
                .filter(blobClient -> fileRequestValidator.validate(fileUploadRequest, blobClient))
                .mapToBoxed(blobClient -> fileMapper.toFile(fileUploadRequest, blobClient))
                .mapToBoxed(fileRepository::save)
                .mapToBoxed(file -> fileMapper.toFileResponse(file, WRITE))
                .orElseThrow(() -> new FileUploadException("File wasn't uploaded. Either entity with target id doesn't" +
                                                           " exist!"));
    }

    @Override
    public Page<FileResponse> getAll(Predicate predicate, Pageable pageable) {
        return fileRepository.findAll(predicate, pageable).map(file -> fileMapper.toFileResponse(file, READ));
    }

    @Override
    public void delete(Long id) {
        Boxed
                .of(id)
                .flatOpt(fileRepository::findById)
                .mapToBoxed(File::getName)
                .mapToBoxed(blobContainerClient::getBlobClient)
                .mapToBoxed(BlobClient::deleteIfExists)
                .ifPresent(result -> fileRepository.deleteById(id));
    }

    @Override
    public FileResponse findById(long id) {
        return Boxed
                .of(id)
                .flatOpt(fileRepository::findById)
                .mapToBoxed(file -> fileMapper.toFileResponse(file, READ))
                .orElseThrow(() -> new FileNotFoundException("File with %s id wasn't found!".formatted(id)));
    }

    private String generateFileName(FileUploadRequest fileUploadRequest) {
        return Boxed
                .of(fileUploadRequest)
                .mapToBoxed(FileUploadRequest::getType)
                .mapToBoxed(MimeType::getSubtype)
                .mapToBoxed(subType -> toFolderName(fileUploadRequest) + UUID.randomUUID() + "." + subType)
                .orElseThrow(() -> new IllegalArgumentException("File upload request doesn't contain any extension!"));
    }

    private String toFolderName(FileUploadRequest fileUploadRequest) {
        return Boxed
                .of(fileUploadRequest)
                .mapToBoxed(FileUploadRequest::getTargetType)
                .mapToBoxed(TargetType::getFolder)
                .mapToBoxed(folder -> folder.formatted(fileUploadRequest.getTargetId()))
                .orElseThrow(() -> new IllegalArgumentException("Wrong target type in request"));

    }

}
