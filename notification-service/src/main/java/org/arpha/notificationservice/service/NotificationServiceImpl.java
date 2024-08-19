package org.arpha.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arpha.domain.type.notification.configuration.EmailNotificationProperties;
import org.arpha.domain.type.notification.configuration.NotificationProperties;
import org.arpha.domain.type.notification.configuration.SmsNotificationProperties;
import org.arpha.notificationservice.service.sender.NotificationSender;
import org.arpha.service.NotificationService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationSender<EmailNotificationProperties> emailNotificationSender;
    private final NotificationSender<SmsNotificationProperties> smsNotificationSender;

    @Override
    public void triggerNotification(NotificationProperties configuration) {
         switch (configuration) {
            case EmailNotificationProperties emailProps -> emailNotificationSender.sendNotification(emailProps);
            case SmsNotificationProperties smsProps -> smsNotificationSender.sendNotification(smsProps);
            default -> throw new IllegalStateException("Notification delivery type is not supported!");
        }
    }

}
