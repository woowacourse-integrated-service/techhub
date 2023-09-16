package com.integrated.techhub.pr.dto.response;

import com.integrated.techhub.pr.domain.PullRequest;
import lombok.Builder;

public record GetPullRequestResponse(
        Long id,
        Long memberId,
        Long missionId,
        String title,
        String status
) {

    @Builder
    public GetPullRequestResponse {
    }

    public static GetPullRequestResponse of(final PullRequest request) {
        return GetPullRequestResponse.builder()
                .id(request.getId())
                .memberId(request.getMemberId())
                .missionId(request.getMissionId())
                .title(request.getTitle())
                .status(request.getStatus().name())
                .build();
    }

}
