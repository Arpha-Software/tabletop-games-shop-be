package org.arpha.domain.type.notification.configuration;

import lombok.Data;
import org.arpha.domain.type.notification.NotificationType;

@Data
public abstract class NotificationProperties {

    private final NotificationType notificationType;

    public abstract void validateParameters();

    public static EmailNotificationProperties.EmailNotificationPropertiesBuilder emailNotification() {
        return EmailNotificationProperties.builder();
    }

    public static SmsNotificationProperties.SmsNotificationPropertiesBuilder smsNotification() {
        return SmsNotificationProperties.builder();
    }

    public abstract static class NotificationPropertiesBuilder<C extends NotificationProperties, B extends NotificationPropertiesBuilder<C, B>> {
        protected NotificationType notificationType;

        public B notificationType(NotificationType notificationType) {
            this.notificationType = notificationType;
            return self();
        }

        protected abstract B self();

        public abstract C build();
    }

}
