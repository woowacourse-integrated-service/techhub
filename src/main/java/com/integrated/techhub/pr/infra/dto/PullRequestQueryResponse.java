package com.integrated.techhub.pr.infra.dto;

import com.querydsl.core.annotations.QueryProjection;

public record PullRequestQueryResponse(
        Long id,
        Long step,
        String title,
        String status
) {

    @QueryProjection
    public PullRequestQueryResponse {
    }

}
