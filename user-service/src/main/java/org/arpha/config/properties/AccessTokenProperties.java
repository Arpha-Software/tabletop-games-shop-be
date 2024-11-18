package org.arpha.config.properties;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.crypto.SecretKey;

@ConfigurationProperties("spring.security.token")
@EnableConfigurationProperties(AccessTokenProperties.class)
public record AccessTokenProperties(String secret, Expiration expiration) {

    public SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public record Expiration(int accessToken, int refreshToken) {
    }

}
