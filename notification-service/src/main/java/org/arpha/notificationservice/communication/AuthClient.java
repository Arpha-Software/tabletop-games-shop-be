package org.arpha.notificationservice.communication;

import org.arpha.notificationservice.dto.KyivstarTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kyivstarAuthenticationClient", url = "${kyivstar.baseUrl}")
public interface AuthClient {

    @PostMapping(value = "/idp/oauth2/token", consumes = "application/x-www-form-urlencoded")
    KyivstarTokenResponse getAccessToken(
            @RequestHeader("Authorization") String authorization,
            @RequestBody String body
    );

}
