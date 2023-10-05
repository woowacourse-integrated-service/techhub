package com.integrated.techhub.common.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.auth.jwt")
public record JwtProperties (
        String header,
        String secretKey,
        Long accessExp,
        Long refreshExp,
        String prefix
) {

}
