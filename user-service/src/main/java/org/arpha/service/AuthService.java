package org.arpha.service;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.user.TokenDetails;
import org.arpha.dto.user.request.RefreshTokenRequest;
import org.arpha.dto.user.response.LoginResponse;
import org.arpha.exception.InternalAuthorizationException;
import org.arpha.security.jwt.JwtUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;

    public LoginResponse reissueAccessToken(RefreshTokenRequest request) {
        if (!jwtUtils.isTokenValid(request.getRefreshToken())) {
            throw new InternalAuthorizationException("Token is not valid or expired!");
        }

        String email = jwtUtils.getSubject(request.getRefreshToken());

        String accessToken = jwtUtils.generateAccessToken(email);
        String refreshToken = jwtUtils.generateRefreshToken(email);

        return LoginResponse.builder()
                .accessToken(TokenDetails.of(accessToken, jwtUtils.getExpirationDate(accessToken)))
                .refreshToken(TokenDetails.of(refreshToken, jwtUtils.getExpirationDate(refreshToken)))
                .build();
    }

}
