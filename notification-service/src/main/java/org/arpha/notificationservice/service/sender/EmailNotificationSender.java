package org.arpha.notificationservice.service.sender;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.core.util.polling.SyncPoller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arpha.domain.type.notification.configuration.EmailNotificationProperties;
import org.arpha.notificationservice.mapper.NotificationMapper;
import org.arpha.notificationservice.repository.NotificationRepository;
import org.arpha.utils.Boxed;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationSender implements NotificationSender<EmailNotificationProperties> {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final EmailClient emailClient;

    @Async
    @Override
    public void sendNotification(EmailNotificationProperties notificationProperties) {
        Boxed.of(notificationProperties)
                .flatMap(this::sendMessage)
                .to(emailResult -> notificationMapper.mapToNotification(notificationProperties, emailResult))
                .apply(notificationRepository::save);
    }

    private Boxed<EmailSendResult> sendMessage(EmailNotificationProperties notificationProperties) {
        return Boxed.of(notificationProperties)
                .to(notificationMapper::mapToEmailMessage)
                .to(emailClient::beginSend)
                .doWith(SyncPoller::waitForCompletion)
                .to(SyncPoller::getFinalResult);
    }

}
