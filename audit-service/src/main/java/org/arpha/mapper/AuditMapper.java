package org.arpha.mapper;

import org.arpha.dto.audit.Action;
import org.arpha.dto.audit.TargetType;
import org.arpha.dto.audit.reponse.AuditResponse;
import org.arpha.entity.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditMapper {


    @Mapping(target = "id", source = "id")
    @Mapping(target = "action", source = "action")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "targetId", source = "targetId")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "targetType", source = "targetType")
    AuditResponse toAuditResponse(Audit audit);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "action", source = "action")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "targetId", source = "targetId")
    @Mapping(target = "targetType", source = "targetType")
    @Mapping(target = "createdAt", ignore = true)
    Audit toAudit(Action action, Long userId, long targetId, TargetType targetType);

}
