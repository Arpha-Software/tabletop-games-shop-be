package org.arpha.service.helper;

import com.azure.storage.blob.BlobClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class MediaServiceHelper {

    private final UserRepository userRepository;

    public boolean validate(FileUploadRequest fileUploadRequest, BlobClient blobClient) {
        return !blobClient.exists() && switch (fileUploadRequest.getTargetType()) {
            case USER -> userRepository.existsById(fileUploadRequest.getTargetId());
            case PRODUCT -> true; //ToDo Add productRepository;
        };
    }

    @SneakyThrows
    public MultipartFile upload(BlobClient blobClient, MultipartFile multipartFile) {
        blobClient.upload(multipartFile.getInputStream(), multipartFile.getSize());
        return multipartFile;
    }

}
