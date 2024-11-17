package org.arpha.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.arpha.security.jwt.JwtUtils;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class AuthMapperHelper {

    private final JwtUtils jwtUtils;

    @Named("toExpirationDate")
    public OffsetDateTime getExpirationDate(String jwtToken) {
        return jwtUtils.getExpirationDate(jwtToken);
    }

}
