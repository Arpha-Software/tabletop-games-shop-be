package org.arpha.dto.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.user.TokenDetails;
import org.arpha.dto.user.UserDetails;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

    private TokenDetails accessToken;
    private TokenDetails refreshToken;
    private UserDetails userDetails;

    public static LoginResponse of(UserResponse userResponse, TokenDetails accessToken, TokenDetails refreshToken) {
        return new LoginResponse(accessToken, refreshToken, UserDetails.of(userResponse));
    }

}
