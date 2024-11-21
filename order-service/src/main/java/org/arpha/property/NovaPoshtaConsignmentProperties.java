package org.arpha.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties("nova-poshta.config")
@EnableConfigurationProperties(NovaPoshtaConsignmentProperties.class)
public record NovaPoshtaConsignmentProperties(String apiKey, String apiUrl, String senderPhone, String senderIdentification) {
}
