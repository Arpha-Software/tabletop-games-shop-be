package org.arpha.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KyivstarTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private long expiresIn;
    private String scope;

    @JsonProperty("token_type")
    private String tokenType;


    public String getBearerToken() {
        return "Bearer " + accessToken;
    }

}
