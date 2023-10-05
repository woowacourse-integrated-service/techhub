package com.integrated.techhub.common.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.Key;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.nio.charset.StandardCharsets.UTF_8;

@ConfigurationProperties(prefix = "spring.auth.jwt")
public record JwtProperties (
        String header,
        String secretKey,
        Long accessExp,
        Long refreshExp,
        String prefix
) {

    public Key getEncodedSecretKey() {
        return hmacShaKeyFor(secretKey().getBytes(UTF_8));
    }

}
