package org.arpha.notificationservice.mapper.helper;

import com.azure.communication.email.models.EmailAddress;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.communication.email.models.EmailSendStatus;
import lombok.RequiredArgsConstructor;
import org.arpha.domain.type.notification.NotificationStatus;
import org.arpha.domain.type.notification.configuration.EmailNotificationProperties;
import org.arpha.domain.type.notification.configuration.SmsNotificationProperties;
import org.arpha.notificationservice.common.SmsSendResult;
import org.arpha.notificationservice.configuration.properties.AzureCommunicationProperties;
import org.mapstruct.Context;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationMapperHelper {

    private final AzureCommunicationProperties azureCommunicationProperties;

    @Named("mapToEmailNotificationStatus")
    public NotificationStatus mapToNotificationStatus(EmailNotificationProperties ignored, @Context EmailSendResult result) {
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
    public NotificationStatus mapToNotificationStatus(SmsNotificationProperties ignored, @Context SmsSendResult result) {
        return result.getStatus();
    }

    @Named("mapToEmailAddress")
    public List<EmailAddress> mapToEmailAddress(String recipientEmail) {
        return List.of(new EmailAddress(recipientEmail));
    }

    @Named("mapToSenderAddress")
    public String mapToSenderAddress(EmailNotificationProperties ignored) {
        return azureCommunicationProperties.senderEmail();
    }

}
