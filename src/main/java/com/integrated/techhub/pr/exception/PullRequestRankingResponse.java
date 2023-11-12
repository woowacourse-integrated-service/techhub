package com.integrated.techhub.pr.exception;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

public record PullRequestRankingResponse(
        String title,
        Double step
) {

    public static PullRequestRankingResponse toResponse(TypedTuple<String> tuple) {
        return new PullRequestRankingResponse(
                tuple.getValue(),
                tuple.getScore()
        );
    }
}
