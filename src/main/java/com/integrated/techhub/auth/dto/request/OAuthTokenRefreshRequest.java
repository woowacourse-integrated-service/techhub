package com.integrated.techhub.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OAuthTokenRefreshRequest(
        @JsonProperty("client_id") String clientId,
        @JsonProperty("client_secret") String clientSecret,
        String grantType,
        String refreshToken
) {
    public OAuthTokenRefreshRequest(String clientId, String clientSecret, String refreshToken) {
        this(clientId, clientSecret, "refresh_token", refreshToken);
    }
}
