package org.arpha.service;

import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.media.response.FileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    FileResponse upload(MultipartFile multipartFile, FileUploadRequest fileUploadRequest);

    Page<FileResponse> getAll(Pageable pageable);

    void delete(Long id);

    FileResponse findById(long id);
}
