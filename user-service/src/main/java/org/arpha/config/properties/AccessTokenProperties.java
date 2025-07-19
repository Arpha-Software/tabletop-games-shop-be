package org.arpha.config.properties;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.crypto.SecretKey;

@ConfigurationProperties("spring.security.token")
@EnableConfigurationProperties(AccessTokenProperties.class)
public class AccessTokenProperties {

    private final String secret;
    private final Expiration expiration;
    private SecretKey secretKey;

    public AccessTokenProperties(String secret, Expiration expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public Expiration expiration() {
        return expiration;
    }

    public record Expiration(int accessToken, int refreshToken) {
    }
}