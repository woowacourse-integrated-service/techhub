package com.integrated.techhub.common.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties (
        String secretKey,
        Long expireLength
) {

}
