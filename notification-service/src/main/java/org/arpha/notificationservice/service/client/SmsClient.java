package org.arpha.notificationservice.service.client;

import lombok.RequiredArgsConstructor;
import org.arpha.notificationservice.common.SmsSendResult;
import org.arpha.notificationservice.dto.SmsServiceResponse;
import org.arpha.notificationservice.mapper.SmsClientMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsClient {

    private final SmsServiceCommunicator communicator;
    private final SmsClientMapper smsClientMapper;

    public SmsSendResult send(String recipientPhoneNumber, String message) {
        SmsServiceResponse response = communicator.send(recipientPhoneNumber, message);
        return smsClientMapper.toSmsSendResult(response);
    }

}
