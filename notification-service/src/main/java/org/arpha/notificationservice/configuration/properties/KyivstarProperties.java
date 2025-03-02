package org.arpha.notificationservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(KyivstarProperties.class)
@ConfigurationProperties(prefix = "kyivstar")
public record KyivstarProperties(
        String clientId,
        String clientSecret,
        String senderName
) {
}
