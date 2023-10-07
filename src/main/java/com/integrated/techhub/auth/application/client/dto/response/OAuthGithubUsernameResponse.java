package com.integrated.techhub.auth.application.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OAuthGithubUsernameResponse(
        @JsonProperty("login") String username
) {

}
