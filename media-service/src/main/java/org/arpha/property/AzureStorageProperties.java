package org.arpha.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("azure.services.storage")
public record AzureStorageProperties(String connectionString, String containerName, long linkExpiration) {
}
