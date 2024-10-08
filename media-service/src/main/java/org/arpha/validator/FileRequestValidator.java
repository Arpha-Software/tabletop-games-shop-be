package org.arpha.validator;

import com.azure.storage.blob.BlobClient;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.service.ProductService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileRequestValidator {

    private final ProductService productService;

    public boolean validate(FileUploadRequest fileUploadRequest, BlobClient blobClient) {
        return !blobClient.exists() && switch (fileUploadRequest.getTargetType()) {
            case PRODUCT -> productService.existProductById(fileUploadRequest.getTargetId());
        };
    }

}
