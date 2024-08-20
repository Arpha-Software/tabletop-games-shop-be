package org.arpha.notificationservice.service.sender;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.models.EmailAddress;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.core.util.polling.SyncPoller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arpha.domain.type.notification.configuration.EmailNotificationProperties;
import org.arpha.notificationservice.configuration.properties.AzureCommunicationProperties;
import org.arpha.notificationservice.domain.Notification;
import org.arpha.notificationservice.domain.type.DeliveryType;
import org.arpha.notificationservice.mapper.NotificationMapper;
import org.arpha.notificationservice.repository.NotificationRepository;
import org.arpha.utils.Boxed;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationSender implements NotificationSender<EmailNotificationProperties> {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final EmailClient emailClient;
    private final AzureCommunicationProperties azureCommunicationProperties;

    @Async
    @Override
    public void sendNotification(EmailNotificationProperties notificationProperties) {
        Boxed.of(notificationProperties)
                .to(this::sendMessage)
                .apply(result -> this.save(result, notificationProperties));
    }

    private EmailSendResult sendMessage(EmailNotificationProperties notificationProperties) {
        EmailMessage emailMessage = new EmailMessage()
                .setSenderAddress(azureCommunicationProperties.getSenderEmail())
                .setToRecipients(new EmailAddress(notificationProperties.getRecipientEmail()))
                .setSubject(notificationProperties.getSubject())
                .setBodyHtml(notificationProperties.getBody());

        SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(emailMessage);
        poller.waitForCompletion();
        return poller.getFinalResult();
    }

    private void save(EmailSendResult result, EmailNotificationProperties notificationProperties) {
        Notification notification = Notification.builder()
                .deliveryType(DeliveryType.EMAIL)
                .notificationType(notificationProperties.getNotificationType())
                .recipient(notificationProperties.getRecipientEmail())
                .status(notificationMapper.mapToNotificationStatus(result.getStatus()))
                .createdAt(OffsetDateTime.now())
                .build();

        notificationRepository.save(notification);
    }

}
