package org.arpha.notificationservice.common;

import lombok.Data;
import org.arpha.domain.type.notification.NotificationStatus;

@Data
public class SmsSendResult {

    private NotificationStatus status;

}
