package org.arpha.notificationservice.mapper.helper;

import org.arpha.domain.type.notification.NotificationStatus;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class SmsClientMapperHelper {

    @Named("toSmsSendStatus")
    public NotificationStatus toSmsSendStatus(int httpStatus) {
        if (httpStatus < 300) {
            return NotificationStatus.SUCCEEDED;
        } else {
            return NotificationStatus.FAILED;
        }
    }

}
