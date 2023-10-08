package com.integrated.techhub.auth.application.client.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubTokenRefreshRequest(
        @JsonProperty("client_id") String clientId,
        @JsonProperty("client_secret") String clientSecret,
        String grantType,
        String refreshToken
) {

    public GithubTokenRefreshRequest(final String clientId, final String clientSecret, final String refreshToken) {
        this(clientId, clientSecret, "refresh_token", refreshToken);
    }

}
