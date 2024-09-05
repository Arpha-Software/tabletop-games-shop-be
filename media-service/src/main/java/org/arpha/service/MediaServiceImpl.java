package org.arpha.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.enums.AccessType;
import org.arpha.dto.media.enums.TargetType;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.media.response.FileResponse;
import org.arpha.entity.File;
import org.arpha.exception.FileNotFoundException;
import org.arpha.exception.FileUploadException;
import org.arpha.exception.GenerateFileLinkException;
import org.arpha.mapper.FileMapper;
import org.arpha.repository.FileRepository;
import org.arpha.utils.Boxed;
import org.arpha.validator.FileRequestValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeType;
import software.amazon.awssdk.awscore.presigner.PresignedRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final AwsRequestService awsRequestService;
    private final FileRequestValidator fileRequestValidator;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final S3Presigner s3Presigner;
    private final S3Client s3client;

    @Override
    @Transactional
    public FileResponse upload(FileUploadRequest fileUploadRequest) {
        return Boxed
                .of(fileUploadRequest)
                .filter(fileRequestValidator::validate)
                .mapToBoxed(fileName1 -> fileMapper.toFile(fileUploadRequest, generateFileName(fileUploadRequest)))
                .mapToBoxed(fileRepository::save)
                .mapToBoxed(file -> fileMapper.toFileResponse(file, AccessType.WRITE,
                        awsRequestService.sendS3Request(file).url().toExternalForm()))
                .orElseThrow(() -> new FileUploadException("File wasn't uploaded. Either entity with target id doesn't" +
                                                           " exist!"));
    }

    @Override
    public Page<FileResponse> getAll(Predicate predicate, Pageable pageable) {
        return fileRepository.findAll(predicate, pageable).map(file -> fileMapper.toFileResponse(file, AccessType.READ, getReadLink(file)));
    }

    private String getReadLink(File file) {
        return Boxed
                .of(file)
                .mapToBoxed(awsRequestService::getObjectPresignRequest)
                .mapToBoxed(s3Presigner::presignGetObject)
                .mapToBoxed(PresignedRequest::url)
                .mapToBoxed(URL::toExternalForm)
                .orElseThrow(() -> new GenerateFileLinkException("Couldn't create link for %s file".formatted(file.getName())));
    }

    @Override
    public void deleteFileById(Long id) {
        Boxed
                .of(id)
                .flatOpt(fileRepository::findById)
                .mapToBoxed(awsRequestService::getDeleteObjectRequest)
                .mapToBoxed(s3client::deleteObject)
                .ifPresent(file -> fileRepository.deleteById(id));
    }

    @Override
    public FileResponse findFileById(long id) {
        return Boxed
                .of(id)
                .flatOpt(fileRepository::findById)
                .mapToBoxed(file -> fileMapper.toFileResponse(file, AccessType.READ, s3Presigner.presignGetObject(
                        awsRequestService.getObjectPresignRequest(file)).url().toExternalForm()))
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
