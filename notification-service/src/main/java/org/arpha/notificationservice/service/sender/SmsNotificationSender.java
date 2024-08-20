package org.arpha.notificationservice.service.sender;

import com.azure.communication.sms.SmsClient;
import com.azure.communication.sms.models.SmsSendResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arpha.domain.type.notification.configuration.SmsNotificationProperties;
import org.arpha.notificationservice.configuration.properties.AzureCommunicationProperties;
import org.arpha.notificationservice.mapper.NotificationMapper;
import org.arpha.notificationservice.repository.NotificationRepository;
import org.arpha.utils.Boxed;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsNotificationSender implements NotificationSender<SmsNotificationProperties> {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final SmsClient smsClient;
    private final AzureCommunicationProperties azureCommunicationProperties;

    @Async
    @Override
    public void sendNotification(SmsNotificationProperties notificationProperties) {
        Boxed.of(notificationProperties)
                .to(this::sendMessage)
                .to(smsSendResult -> notificationMapper.mapToNotification(notificationProperties, smsSendResult))
                .apply(notificationRepository::save);
    }

    private SmsSendResult sendMessage(SmsNotificationProperties notificationProperties) {
        return smsClient.send(
                azureCommunicationProperties.senderPhoneNumber(),
                notificationProperties.getRecipientPhoneNumber(),
                notificationProperties.getMessage(),
                null
        );
    }

}
