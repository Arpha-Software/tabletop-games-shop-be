package org.arpha.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AzureStorageProperties.class)
@ConfigurationProperties("azure.services.storage")
public record AzureStorageProperties(String connectionString, String containerName, long linkExpiration) {
}
