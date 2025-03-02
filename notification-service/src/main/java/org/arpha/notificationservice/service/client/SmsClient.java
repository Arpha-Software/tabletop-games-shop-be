package org.arpha.notificationservice.service.client;

import lombok.RequiredArgsConstructor;
import org.arpha.domain.type.notification.NotificationStatus;
import org.arpha.notificationservice.common.SmsSendResult;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsClient {

    private final SmsServiceCommunicator communicator;

    public SmsSendResult send(String recipientPhoneNumber, String message) {
        communicator.send(recipientPhoneNumber, message);
        return new SmsSendResult(NotificationStatus.SUCCEEDED);
    }

}
