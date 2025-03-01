package org.arpha.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.arpha.notificationservice.communication.AuthClient;
import org.arpha.notificationservice.configuration.properties.KyivstarProperties;
import org.arpha.notificationservice.dto.KyivstarTokenResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class KyivstarAuthService {

    private final AuthClient authClient;
    private final KyivstarProperties kyivstarProperties;

    @Cacheable("kyivstarAccessToken")
    public KyivstarTokenResponse getAccessToken() {
        return authClient.getAccessToken(
                getAuthorization(),
                "grant_type=client_credentials"
        );
    }


    private String getAuthorization() {
        String auth = kyivstarProperties.clientId() + ":" + kyivstarProperties.clientSecret();
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }

}
