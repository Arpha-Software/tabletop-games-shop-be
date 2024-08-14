package org.arpha.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String username;
    private String token;
    //UserDetails firstName lastName email
    //TokenDetails token, expires (LocalDateTime)
}
