package com.integrated.techhub.auth.application.client;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.github")
public record GithubClientProperties (
        String clientId,
        String clientSecret
) {
}
