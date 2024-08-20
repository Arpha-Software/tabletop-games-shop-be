package org.arpha.notificationservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AzureCommunicationProperties.class)
@ConfigurationProperties(prefix = "azure.services.communication")
public record AzureCommunicationProperties(
        String connectionString,
        String senderEmail,
        String senderPhoneNumber
) {
}
