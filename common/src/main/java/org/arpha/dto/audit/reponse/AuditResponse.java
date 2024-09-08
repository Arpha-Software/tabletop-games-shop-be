package org.arpha.dto.audit.reponse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.audit.Action;
import org.arpha.dto.audit.TargetType;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditResponse {

    private long id;
    private Action action;
    private Long userId;
    private Long targetId;
    private TargetType targetType;
    private OffsetDateTime createdAt;

}
