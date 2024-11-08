package org.arpha.validator;

import com.azure.storage.blob.BlobClient;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.request.FileUploadRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileRequestValidator {

    public boolean validate(FileUploadRequest fileUploadRequest, BlobClient blobClient) {
        return !blobClient.exists() && switch (fileUploadRequest.getTargetType()) {
            case PRODUCT, PRODUCT_MAIN_IMG -> true;
        };
    }

}
