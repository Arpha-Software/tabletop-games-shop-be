package org.arpha.notificationservice.mapper;

import com.azure.communication.email.models.EmailSendStatus;
import org.arpha.domain.type.notification.NotificationStatus;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface NotificationMapper {

    default NotificationStatus mapToNotificationStatus(EmailSendStatus emailSendStatus) {
        return switch (emailSendStatus.toString()) {
            case "NotStarted" -> NotificationStatus.NOT_STARTED;
            case "Running" -> NotificationStatus.RUNNING;
            case "Succeeded" -> NotificationStatus.SUCCEEDED;
            case "Failed" -> NotificationStatus.FAILED;
            case "Canceled" -> NotificationStatus.CANCELED;
            default -> throw new IllegalArgumentException("Unknown EmailSendStatus: " + emailSendStatus);
        };
    }

}
