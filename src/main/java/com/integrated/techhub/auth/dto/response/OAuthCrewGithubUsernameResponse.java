package com.integrated.techhub.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OAuthCrewGithubUsernameResponse(
        @JsonProperty("login") String username
) {

}
