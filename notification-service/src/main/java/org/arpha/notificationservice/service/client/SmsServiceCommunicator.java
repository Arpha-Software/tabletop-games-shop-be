package org.arpha.notificationservice.service.client;

import lombok.RequiredArgsConstructor;
import org.arpha.notificationservice.communication.SmsServiceClient;
import org.arpha.notificationservice.configuration.properties.SmsClientProperties;
import org.arpha.notificationservice.dto.SmsSendRequest;
import org.arpha.notificationservice.dto.SmsServiceResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SmsServiceCommunicator {

    private final SmsServiceClient smsServiceClient;
    private final SmsClientProperties smsClientProperties;

    public SmsServiceResponse send(List<String> recipientPhoneNumbers, String content) {
        String authorizationHeader = "Bearer " + smsClientProperties.apiKey();
        return smsServiceClient.sendSms(authorizationHeader, getSendRequest(recipientPhoneNumbers, content));
    }

    public SmsServiceResponse send(String recipientPhoneNumber, String content) {
        String authorizationHeader = "Bearer " + smsClientProperties.apiKey();
        return smsServiceClient.sendSms(authorizationHeader, getSendRequest(List.of(recipientPhoneNumber), content));
    }

    private SmsSendRequest getSendRequest(List<String> phoneNumbers, String content) {
        String phoneNumbersUnified = String.join(",", phoneNumbers);
        return new SmsSendRequest(smsClientProperties.senderName(), phoneNumbersUnified, content);
    }

}
