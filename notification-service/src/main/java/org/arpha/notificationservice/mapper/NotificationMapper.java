package org.arpha.notificationservice.mapper;

import com.azure.communication.email.models.EmailSendResult;
import com.azure.communication.email.models.EmailSendStatus;
import com.azure.communication.sms.models.SmsSendResult;
import org.arpha.domain.type.notification.NotificationStatus;
import org.arpha.domain.type.notification.configuration.EmailNotificationProperties;
import org.arpha.domain.type.notification.configuration.SmsNotificationProperties;
import org.arpha.notificationservice.domain.Notification;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface NotificationMapper {

    @Mapping(target = "notificationType", source = "properties.notificationType")
    @Mapping(target = "recipient", source = "properties.recipientEmail")
    @Mapping(target = "deliveryType", constant = "EMAIL")
    @Mapping(target = "status", source = "properties", qualifiedByName = "mapToEmailNotificationStatus")
    Notification mapToNotification(EmailNotificationProperties properties, @Context EmailSendResult result);

    @Mapping(target = "notificationType", source = "properties.notificationType")
    @Mapping(target = "recipient", source = "properties.recipientPhoneNumber")
    @Mapping(target = "deliveryType", constant = "SMS")
    @Mapping(target = "status", source = "properties", qualifiedByName = "mapToSmsNotificationStatus")
    Notification mapToNotification(SmsNotificationProperties properties, @Context SmsSendResult result);

    @Named("mapToEmailNotificationStatus")
    default NotificationStatus mapToNotificationStatus(EmailNotificationProperties properties, @Context EmailSendResult result) {
        EmailSendStatus emailSendStatus = result.getStatus();
        return switch (emailSendStatus.toString()) {
            case "NotStarted" -> NotificationStatus.NOT_STARTED;
            case "Running" -> NotificationStatus.RUNNING;
            case "Succeeded" -> NotificationStatus.SUCCEEDED;
            case "Failed" -> NotificationStatus.FAILED;
            case "Canceled" -> NotificationStatus.CANCELED;
            default -> throw new IllegalArgumentException("Unknown EmailSendStatus: " + emailSendStatus);
        };
    }

    @Named("mapToSmsNotificationStatus")
    default NotificationStatus mapToNotificationStatus(SmsNotificationProperties properties, @Context SmsSendResult result) {
        if (result.isSuccessful()) {
            return NotificationStatus.SUCCEEDED;
        } else {
            return NotificationStatus.FAILED;
        }
    }

}
