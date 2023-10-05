package com.integrated.techhub.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.integrated.techhub.auth.domain.AccessToken;
import com.integrated.techhub.auth.domain.RefreshToken;

public record OAuthTokensResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") Long accessTokenExp,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("refresh_token_expires_in") Long refreshTokenExp
) {
    public AccessToken toAccessToken(final String email) {
        return AccessToken.builder()
                .email(email)
                .token(accessToken)
                .ttl(accessTokenExp)
                .build();
    }

    public RefreshToken toRefreshToken(final String email) {
        return RefreshToken.builder()
                .email(email)
                .token(refreshToken)
                .ttl(refreshTokenExp)
                .build();
    }
}
