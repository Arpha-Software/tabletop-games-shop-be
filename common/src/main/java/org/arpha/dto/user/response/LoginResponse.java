package org.arpha.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.user.TokenDetails;
import org.arpha.dto.user.UserDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private TokenDetails tokenDetails;
    private UserDetails userDetails;

}
