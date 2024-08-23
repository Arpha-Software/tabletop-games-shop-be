package org.arpha.service;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.media.response.FileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MediaService {

    FileResponse upload(FileUploadRequest fileUploadRequest);

    Page<FileResponse> getAll(Predicate predicate, Pageable pageable);

    void delete(Long id);

    FileResponse findById(long id);
}
