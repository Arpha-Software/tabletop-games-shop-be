package org.arpha.notificationservice.service.sender;


import org.arpha.domain.type.notification.configuration.NotificationProperties;

public interface NotificationSender<T extends NotificationProperties> {

    void sendNotification(T notificationConfiguration);

}
