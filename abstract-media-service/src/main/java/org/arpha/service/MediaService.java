package org.arpha.service;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.media.enums.TargetType;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.dto.media.response.FileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface MediaService {

    FileResponse upload(FileUploadRequest fileUploadRequest);
    Page<FileResponse> getAll(Predicate predicate, Pageable pageable);
    void deleteFileById(Long id);
    FileResponse findFileById(long id);
    String getFileLink(long targetId, TargetType targetType);
    List<String> getFilesLinks(long targetId, TargetType targetType);
    void deleteAllByTargetIdAndType(long targetId, TargetType targetType);
    Map<Long, List<String>> getFileLinksForProducts(List<Long> productIds, TargetType targetType);

}
