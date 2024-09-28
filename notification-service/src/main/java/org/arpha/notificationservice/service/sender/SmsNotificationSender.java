package org.arpha.notificationservice.service.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arpha.domain.type.notification.configuration.SmsNotificationProperties;
import org.arpha.notificationservice.common.SmsSendResult;
import org.arpha.notificationservice.mapper.NotificationMapper;
import org.arpha.notificationservice.repository.NotificationRepository;
import org.arpha.notificationservice.service.client.SmsClient;
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

    @Async
    @Override
    public void sendNotification(SmsNotificationProperties notificationProperties) {
        Boxed.of(notificationProperties)
                .to(this::sendMessage)
                .to(smsSendResult -> notificationMapper.mapToNotification(notificationProperties, smsSendResult))
                .to(notificationRepository::save)
                .apply(notification ->
                        log.info("Notification with id={} sent with status {}", notification.getId(), notification.getStatus()));
    }

    private SmsSendResult sendMessage(SmsNotificationProperties notificationProperties) {
        return smsClient.send(
                notificationProperties.getRecipientPhoneNumber(),
                notificationProperties.getMessage()
        );
    }

}
