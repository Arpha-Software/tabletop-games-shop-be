package org.arpha.service;

import org.arpha.domain.type.notification.configuration.NotificationProperties;

public interface NotificationService {

    void triggerNotification(NotificationProperties properties);

}
