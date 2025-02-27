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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.arpha.dto.media.enums.AccessType.READ;
import static org.arpha.dto.media.enums.AccessType.WRITE;


@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final BlobContainerClient blobContainerClient;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final BlobService blobService;

    @Override
    public FileResponse upload(FileUploadRequest fileUploadRequest) {
        return Boxed
                .of(fileUploadRequest)
                .mapToBoxed(this::generateFileName)
                .mapToBoxed(blobContainerClient::getBlobClient)
                .mapToBoxed(blobClient -> fileMapper.toFile(fileUploadRequest, blobClient))
                .doWith(this::deleteOldMainImgIfPresent)
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
    public void deleteFileById(Long id) {
        Boxed
                .of(id)
                .flatOpt(fileRepository::findById)
                .mapToBoxed(File::getName)
                .mapToBoxed(blobContainerClient::getBlobClient)
                .mapToBoxed(BlobClient::deleteIfExists)
                .ifPresent(result -> fileRepository.deleteById(id));
    }

    @Override
    public void deleteAllByTargetIdAndType(long targetId, TargetType targetType) {
        fileRepository.deleteByTargetIdAndTargetType(targetId, targetType);
    }

    @Override
    public FileResponse findFileById(long id) {
        return Boxed
                .of(id)
                .flatOpt(fileRepository::findById)
                .mapToBoxed(file -> fileMapper.toFileResponse(file, READ))
                .orElseThrow(() -> new FileNotFoundException("File with %s id wasn't found!".formatted(id)));
    }

    @Override
    public String getFileLink(long targetId, TargetType targetType) {
        return Boxed
                .of(targetId)
                .mapToBoxed(targetId1 -> fileRepository.findByTargetIdAndTargetType(targetId1, targetType))
                .mapToBoxed(file -> blobService.generateLink(file.getName(), READ))
                .orElse(null);
    }

    @Override
    public List<String> getFilesLinks(long targetId, TargetType targetType) {
        return Boxed
                .of(targetId)
                .mapToBoxed(targetId1 -> fileRepository.findAllByTargetIdAndTargetType(targetId1, targetType))
                .orElseGet(ArrayList::new)
                .stream()
                .map(file -> blobService.generateLink(file.getName(), READ))
                .toList();
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

    private void deleteOldMainImgIfPresent(File file) {
        Boxed
                .of(file)
                .filter(file1 -> file1.getTargetType().equals(TargetType.PRODUCT_MAIN_IMG))
                .mapToBoxed(File::getTargetId)
                .ifPresent(id -> fileRepository.deleteByTargetIdAndTargetType(id, TargetType.PRODUCT_MAIN_IMG));
    }

}
