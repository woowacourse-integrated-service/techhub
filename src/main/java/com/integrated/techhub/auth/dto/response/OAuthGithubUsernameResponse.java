package com.integrated.techhub.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OAuthGithubUsernameResponse(
        @JsonProperty("login") String username
) {

}
