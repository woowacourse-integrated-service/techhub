package com.integrated.techhub.auth.application.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.integrated.techhub.auth.domain.AccessToken;
import com.integrated.techhub.auth.domain.RefreshToken;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static com.integrated.techhub.auth.domain.type.Type.GITHUB;

@JsonNaming(SnakeCaseStrategy.class)
public record OAuthTokensResponse (
        String accessToken,
        String refreshToken,
        @JsonProperty("expires_in") Long accessTokenExp,
        @JsonProperty("refresh_token_expires_in") Long refreshTokenExp
) {
    public AccessToken toAccessToken(final Long memberId) {
        return AccessToken.builder()
                .memberId(memberId)
                .token(accessToken)
                .ttl(accessTokenExp)
                .type(GITHUB)
                .build();
    }

    public RefreshToken toRefreshToken(final Long memberId) {
        return RefreshToken.builder()
                .memberId(memberId)
                .token(refreshToken)
                .ttl(refreshTokenExp)
                .type(GITHUB)
                .build();
    }

}
