package org.arpha.notificationservice.communication;

import feign.Headers;
import org.arpha.notificationservice.dto.SmsSendRequest;
import org.arpha.notificationservice.dto.SmsServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "releansClient", url = "https://api.releans.com/v2")
public interface SmsServiceClient {

    @PostMapping("/message")
    @Headers("Authorization: Bearer {apiKey}")
    SmsServiceResponse sendSms(@RequestHeader("Authorization") String authorization,
                               @RequestBody SmsSendRequest smsSendRequest);

}
