package org.arpha.domain.type.notification.configuration;

import lombok.Getter;
import org.arpha.domain.type.notification.NotificationType;

@Getter
public class SmsNotificationProperties extends NotificationProperties {

    private final String message;
    private final String recipientPhoneNumber;

    protected SmsNotificationProperties(NotificationType notificationType, String message, String recipientPhoneNumber) {
        super(notificationType);
        this.message = message;
        this.recipientPhoneNumber = recipientPhoneNumber;
    }

    @Override
    public void validateParameters() {
        if (recipientPhoneNumber == null || message == null) {
            throw new IllegalArgumentException("SMS parameters cannot be null");
        }
    }

    public static SmsNotificationPropertiesBuilder builder() {
        return new SmsNotificationPropertiesBuilder();
    }

    public static class SmsNotificationPropertiesBuilder
            extends NotificationPropertiesBuilder<SmsNotificationProperties, SmsNotificationPropertiesBuilder> {

        private String message;
        private String recipientPhoneNumber;

        public SmsNotificationPropertiesBuilder message(String message) {
            this.message = message;
            return this;
        }

        public SmsNotificationPropertiesBuilder recipientPhoneNumber(String recipientPhoneNumber) {
            this.recipientPhoneNumber = recipientPhoneNumber;
            return this;
        }

        @Override
        public SmsNotificationProperties build() {
            SmsNotificationProperties config = new SmsNotificationProperties(notificationType, message, recipientPhoneNumber);
            config.validateParameters();
            return config;
        }

        @Override
        protected SmsNotificationPropertiesBuilder self() {
            return this;
        }

    }

}
