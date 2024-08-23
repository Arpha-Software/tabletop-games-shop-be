package org.arpha.validator;

import com.azure.storage.blob.BlobClient;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.service.UserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileRequestValidator {

    private final UserService userService;

    public boolean validate(FileUploadRequest fileUploadRequest, BlobClient blobClient) {
        return !blobClient.exists() && switch (fileUploadRequest.getTargetType()) {
            case USER -> userService.existById(fileUploadRequest.getTargetId());
            case PRODUCT -> true; //ToDo Add productService;
        };
    }

}
