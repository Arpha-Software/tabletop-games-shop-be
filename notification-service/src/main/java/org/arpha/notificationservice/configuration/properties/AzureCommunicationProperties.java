package org.arpha.notificationservice.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "azure.services.communication")
@EnableConfigurationProperties(AzureCommunicationProperties.class)
public class AzureCommunicationProperties {

    private String connectionString;
    private String senderEmail;
    private String senderPhoneNumber;

}
