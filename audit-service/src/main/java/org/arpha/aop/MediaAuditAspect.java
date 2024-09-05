package org.arpha.aop;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.audit.TargetType;
import org.arpha.dto.media.response.FileResponse;
import org.arpha.service.AuditService;
import org.arpha.utills.AspectUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static org.arpha.dto.audit.Action.DELETE_FILE_BY_ID;
import static org.arpha.dto.audit.Action.FIND_FILE_BY_ID;
import static org.arpha.dto.audit.Action.UPLOAD_FILE;

@Component
@Aspect
@RequiredArgsConstructor
public class MediaAuditAspect {

    private final AuditService auditService;

    @AfterReturning(
            value = "execution(public org.arpha.dto.media.response.FileResponse upload(org.arpha.dto.media.request.FileUploadRequest))",
            argNames = "fileResponse",
            returning = "fileResponse")
    public void uploadFileAdvice(FileResponse fileResponse) {
        auditService.saveAudit(UPLOAD_FILE, fileResponse.getId(), AspectUtils.getAuthenticatedUserId(), TargetType.FILE);
    }

    @AfterReturning(value = "execution(public void deleteFileById(Long)) && args(id)",
            argNames="id")
    public void deleteFileByIdAdvice(long id) {
        auditService.saveAudit(DELETE_FILE_BY_ID, id, AspectUtils.getAuthenticatedUserId(), TargetType.FILE);
    }

    @AfterReturning(
            value = "execution(public org.arpha.dto.media.response.FileResponse findFileById(long))",
            argNames = "fileResponse",
            returning = "fileResponse")
    public void findFileByIdAdvice(FileResponse fileResponse) {
        auditService.saveAudit(FIND_FILE_BY_ID, fileResponse.getId(), AspectUtils.getAuthenticatedUserId(), TargetType.FILE);
    }

}
