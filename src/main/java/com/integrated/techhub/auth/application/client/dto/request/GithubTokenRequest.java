package com.integrated.techhub.auth.application.client.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubTokenRequest(
        @JsonProperty("client_id")
        String clientId,
        @JsonProperty("client_secret")
        String clientSecret,
        String code
) {

}