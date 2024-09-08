package org.arpha.validator;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.service.UserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileRequestValidator {

    private final UserService userService;

    public boolean validate(FileUploadRequest fileUploadRequest) {
        return switch (fileUploadRequest.getTargetType()) {
            case PRODUCT -> true; //ToDo Add productService;
        };
    }

}
