package org.arpha.service;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.audit.Action;
import org.arpha.dto.audit.TargetType;
import org.arpha.dto.audit.reponse.AuditResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditService {

    Page<AuditResponse> findAll(Predicate predicate, Pageable pageable);

    void delete(long id);

    AuditResponse saveAudit(Action action, Long targetId, Long userId, TargetType targetType);
}
