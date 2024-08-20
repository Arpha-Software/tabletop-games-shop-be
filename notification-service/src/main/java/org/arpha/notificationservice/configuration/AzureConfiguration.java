package org.arpha.notificationservice.configuration;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.sms.SmsClient;
import com.azure.communication.sms.SmsClientBuilder;
import lombok.RequiredArgsConstructor;
import org.arpha.notificationservice.configuration.properties.AzureCommunicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AzureConfiguration {

    private final AzureCommunicationProperties azureCommunicationProperties;

    @Bean
    public EmailClient azureEmailClient() {
        return new EmailClientBuilder()
                .connectionString(azureCommunicationProperties.connectionString())
                .buildClient();
    }

    @Bean
    public SmsClient smsClient() {
        return new SmsClientBuilder()
                .connectionString(azureCommunicationProperties.connectionString())
                .buildClient();
    }

}
