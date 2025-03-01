package org.arpha.notificationservice.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.arpha.domain.type.notification.NotificationStatus;

@Data
@AllArgsConstructor
public class SmsSendResult {

    private NotificationStatus status;

}
