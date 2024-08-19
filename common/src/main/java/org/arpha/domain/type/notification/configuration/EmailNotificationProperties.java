package org.arpha.domain.type.notification.configuration;

import lombok.Getter;
import org.arpha.domain.type.notification.NotificationType;

@Getter
public class EmailNotificationProperties extends NotificationProperties {

    private final String subject;
    private final String body;
    private final String recipientEmail;

    protected EmailNotificationProperties(NotificationType notificationType, String subject, String body, String recipientEmail) {
        super(notificationType);
        this.subject = subject;
        this.body = body;
        this.recipientEmail = recipientEmail;
    }

    @Override
    public void validateParameters() {
        if (recipientEmail == null || subject == null || body == null) {
            throw new IllegalArgumentException("Email parameters cannot be null");
        }
    }

    public static EmailNotificationPropertiesBuilder builder() {
        return new EmailNotificationPropertiesBuilder();
    }

    public static class EmailNotificationPropertiesBuilder
            extends NotificationPropertiesBuilder<EmailNotificationProperties, EmailNotificationPropertiesBuilder> {

        private String subject;
        private String body;
        private String recipientEmail;

        public EmailNotificationPropertiesBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailNotificationPropertiesBuilder body(String body) {
            this.body = body;
            return this;
        }

        public EmailNotificationPropertiesBuilder recipientEmail(String recipientEmail) {
            this.recipientEmail = recipientEmail;
            return this;
        }

        @Override
        public EmailNotificationProperties build() {
            EmailNotificationProperties config = new EmailNotificationProperties(notificationType, subject, body, recipientEmail);
            config.validateParameters();
            return config;
        }

        @Override
        protected EmailNotificationPropertiesBuilder self() {
            return this;
        }

    }

}
