package org.arpha.notificationservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(SmsClientProperties.class)
@ConfigurationProperties(prefix = "releans")
public record SmsClientProperties(
        String apiKey,
        String senderName
) {
}
