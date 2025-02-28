package org.arpha.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arpha.config.properties.AccessTokenProperties;
import org.arpha.dto.user.TokenDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final AccessTokenProperties properties;

    public TokenDetails generateAccessToken(String username) {
        return generateToken(username, properties.expiration().accessToken());
    }

    public TokenDetails generateRefreshToken(String username) {
        return generateToken(username, properties.expiration().refreshToken());
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(properties.getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            log.error("Error happened during token validation with message {}", e.getMessage(), e);
        }
        return false;
    }

    public String getSubject(String token) {
        return Jwts
                .parser()
                .verifyWith(properties.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public LocalDateTime getExpirationDate(String token) {
        return Jwts
                .parser()
                .verifyWith(properties.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private TokenDetails generateToken(String username, int expiration) {
        Date expirationDate = new Date(System.currentTimeMillis() + (expiration * 1000L));

        String token = Jwts
                .builder()
                .issuer("tabletop-games-shop")
                .subject(username)
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(properties.getSecretKey())
                .compact();

        return TokenDetails.of(token, getExpirationDate(token));
    }

}
