package org.arpha.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.media.response.FileResponse;
import org.arpha.entity.File;
import org.arpha.exception.FileNotFoundException;
import org.arpha.exception.FileUploadException;
import org.arpha.mapper.FileMapper;
import org.arpha.repository.FileRepository;
import org.arpha.service.helper.MediaServiceHelper;
import org.arpha.utils.Boxed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static org.arpha.dto.media.enums.AccessType.READ;
import static org.arpha.dto.media.enums.AccessType.WRITE;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final BlobContainerClient blobContainerClient;
    private final MediaServiceHelper mediaServiceHelper;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public FileResponse upload(@NonNull MultipartFile multipartFile, FileUploadRequest fileUploadRequest) {
        return Boxed
                .of(multipartFile)
                .mapToBoxed(MultipartFile::getOriginalFilename)
                .mapToBoxed(blobContainerClient::getBlobClient)
                .filter(blobClient -> mediaServiceHelper.validate(fileUploadRequest, blobClient))
                .mapToBoxed(blobClient -> mediaServiceHelper.upload(blobClient, multipartFile))
                .mapToBoxed(multipartFile1 -> fileMapper.toFile(multipartFile, fileUploadRequest))
                .mapToBoxed(fileRepository::save)
                .mapToBoxed(file -> fileMapper.toFileResponse(file, WRITE))
                .orElseThrow(() -> new FileUploadException("File wasn't uploaded. Either entity with target id doesn't" +
                                                           " exist or file with that name already exist in storage"));
    }

    @Override
    public Page<FileResponse> getAll(Pageable pageable) {
        return fileRepository.findAll(pageable).map(file -> fileMapper.toFileResponse(file, READ));
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
}
