package com.integrated.techhub.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.integrated.techhub.auth.domain.AccessToken;
import com.integrated.techhub.auth.domain.RefreshToken;

import java.io.Serializable;

public record OAuthTokensResponse (
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") Long accessTokenExp,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("refresh_token_expires_in") Long refreshTokenExp
) {
    public AccessToken toAccessToken(final Long memberId) {
        return AccessToken.builder()
                .memberId(memberId)
                .token(accessToken)
                .ttl(accessTokenExp)
                .type("GITHUB")
                .build();
    }

    public RefreshToken toRefreshToken(final Long memberId) {
        return RefreshToken.builder()
                .memberId(memberId)
                .token(refreshToken)
                .ttl(refreshTokenExp)
                .type("GITHUB")
                .build();
    }

}
