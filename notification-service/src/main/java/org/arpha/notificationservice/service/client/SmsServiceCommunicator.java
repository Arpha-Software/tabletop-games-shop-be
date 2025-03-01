package org.arpha.notificationservice.service.client;

import lombok.RequiredArgsConstructor;
import org.arpha.notificationservice.communication.SmsServiceClient;
import org.arpha.notificationservice.configuration.properties.KyivstarProperties;
import org.arpha.notificationservice.dto.SmsSendRequest;
import org.arpha.notificationservice.dto.SmsServiceResponse;
import org.arpha.notificationservice.service.KyivstarAuthService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SmsServiceCommunicator {

    private final SmsServiceClient smsServiceClient;
    private final KyivstarProperties kyivstarProperties;
    private final KyivstarAuthService kyivstarAuthService;

    public SmsServiceResponse send(List<String> recipientPhoneNumbers, String content) {
        return smsServiceClient.sendSms(
                kyivstarAuthService.getAccessToken().getBearerToken(),
                getSendRequest(recipientPhoneNumbers, content)
        );
    }

    public SmsServiceResponse send(String recipientPhoneNumber, String content) {
        return smsServiceClient.sendSms(
                kyivstarAuthService.getAccessToken().getBearerToken(),
                getSendRequest(List.of(recipientPhoneNumber), content)
        );
    }

    private SmsSendRequest getSendRequest(List<String> phoneNumbers, String content) {
        String phoneNumbersUnified = String.join(",", phoneNumbers);
        return new SmsSendRequest(kyivstarProperties.senderName(), phoneNumbersUnified, content);
    }

}
