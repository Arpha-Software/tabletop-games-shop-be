package org.arpha.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties("nova-poshta.sender-info")
@EnableConfigurationProperties(NovaPoshtaSenderProperties.class)
public record NovaPoshtaSenderProperties(String city, String departmentName, String senderPhone, String senderFirstName,
                                         String senderMiddleName, String senderLastName) {
}
