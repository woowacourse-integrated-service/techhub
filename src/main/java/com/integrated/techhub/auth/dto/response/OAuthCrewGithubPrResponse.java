package com.integrated.techhub.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record OAuthCrewGithubPrResponse(
        @JsonProperty("html_url") String url,
        @JsonProperty("title") String title,
        @JsonProperty("number") Integer number,
        @JsonProperty("state") String state,
        @JsonProperty("created_at") Date createdAt,
        @JsonProperty("merged_at") Date mergedAt,
        UserDto user
) {

    public record UserDto(
            @JsonProperty("login") String login
    ) {
    }

}
