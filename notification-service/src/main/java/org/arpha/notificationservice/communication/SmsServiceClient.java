package org.arpha.notificationservice.communication;

import org.arpha.notificationservice.dto.SmsSendRequest;
import org.arpha.notificationservice.dto.SmsServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "kyivstarClient",
        url = "${kyivstar.baseUrl}${kyivstar.environment}/rest/${kyivstar.apiVersion}"
)
public interface SmsServiceClient {

    @PostMapping("/sms")
    SmsServiceResponse sendSms(@RequestHeader("Authorization") String authorization,
                           @RequestBody SmsSendRequest smsSendRequest);

}
