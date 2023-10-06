package com.integrated.techhub.pr.dto.response;

import com.integrated.techhub.pr.infra.dto.PullRequestQueryResponse;

public record PullRequestResponse (
        Long id,
        Long step,
        String title,
        String status
) {

    public static PullRequestResponse from(final PullRequestQueryResponse response) {
        return new PullRequestResponse(
                response.id(),
                response.step(),
                response.title(),
                response.status()
        );
    }
}
