package org.arpha.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.audit.Action;
import org.arpha.dto.audit.TargetType;
import org.arpha.dto.audit.reponse.AuditResponse;
import org.arpha.entity.Audit;
import org.arpha.mapper.AuditMapper;
import org.arpha.repository.AuditRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    @Override
    public Page<AuditResponse> findAll(Predicate predicate, Pageable pageable) {
        return auditRepository.findAll(predicate, pageable).map(auditMapper::toAuditResponse);
    }

    @Override
    public void delete(long id) {
        auditRepository.deleteById(id);
    }

    @Override
    public AuditResponse saveAudit(Action action, Long targetId, Long userId, TargetType targetType) {
        Audit audit = auditMapper.toAudit(action, userId, targetId, targetType);
        audit = auditRepository.save(audit);
        return auditMapper.toAuditResponse(audit);
    }

}
