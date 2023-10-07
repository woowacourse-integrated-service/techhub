package com.integrated.techhub.auth.application.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.integrated.techhub.pr.domain.PullRequest;
import com.integrated.techhub.pr.domain.type.State;

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

    public PullRequest toPullRequest(final Long memberId, final Long stepId) {
        return PullRequest.builder()
                .title(title)
                .memberId(memberId)
                .stepId(stepId)
                .state(State.valueOf(state.toUpperCase()))
                .build();
    }

}
