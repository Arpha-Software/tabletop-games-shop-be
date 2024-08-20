package org.arpha.notificationservice.service.sender;

import com.azure.communication.sms.SmsClient;
import com.azure.communication.sms.models.SmsSendResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arpha.domain.type.notification.NotificationStatus;
import org.arpha.domain.type.notification.configuration.SmsNotificationProperties;
import org.arpha.notificationservice.configuration.properties.AzureCommunicationProperties;
import org.arpha.notificationservice.domain.Notification;
import org.arpha.notificationservice.domain.type.DeliveryType;
import org.arpha.notificationservice.repository.NotificationRepository;
import org.arpha.utils.Boxed;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsNotificationSender implements NotificationSender<SmsNotificationProperties> {

    private final SmsClient smsClient;
    private final AzureCommunicationProperties azureCommunicationProperties;
    private final NotificationRepository notificationRepository;

    @Async
    @Override
    public void sendNotification(SmsNotificationProperties notificationProperties) {
        Boxed.of(notificationProperties)
                .to(this::sendMessage)
                .apply(result -> this.save(result, notificationProperties));
    }

    private SmsSendResult sendMessage(SmsNotificationProperties notificationProperties) {
        return smsClient.send(
                azureCommunicationProperties.senderPhoneNumber(),
                notificationProperties.getRecipientPhoneNumber(),
                notificationProperties.getMessage(),
                null
        );
    }

    private void save(SmsSendResult result, SmsNotificationProperties notificationProperties) {
        Notification notification = Notification.builder()
                .deliveryType(DeliveryType.SMS)
                .notificationType(notificationProperties.getNotificationType())
                .recipient(notificationProperties.getRecipientPhoneNumber())
                .status(getSmsStatus(result))
                .createdAt(OffsetDateTime.now())
                .build();

        notificationRepository.save(notification);
    }

    private NotificationStatus getSmsStatus(SmsSendResult result) {
        if (result.isSuccessful()) {
            log.info("Message has been sent successfully!");
            return NotificationStatus.SUCCEEDED;
        }
        log.error("Sms has failed! Error message: {}", result.getErrorMessage());
        return NotificationStatus.FAILED;
    }

}
