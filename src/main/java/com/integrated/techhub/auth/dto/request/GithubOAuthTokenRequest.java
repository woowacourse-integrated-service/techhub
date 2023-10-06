package com.integrated.techhub.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubOAuthTokenRequest(
        @JsonProperty("client_id")
        String clientId,
        @JsonProperty("client_secret")
        String clientSecret,
        String code
) {

}