package com.integrated.techhub.auth.application.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.integrated.techhub.pr.domain.PullRequest;
import com.integrated.techhub.pr.domain.type.State;

import java.util.Date;

@JsonNaming(SnakeCaseStrategy.class)
public record GithubPrInfoResponse(
        @JsonProperty("html_url") String url,
        String title,
        Integer number,
        String state,
        Date createdAt,
        Date mergedAt,
        UserDto user
) {

    @JsonNaming(SnakeCaseStrategy.class)
    public record UserDto(
            String login
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
