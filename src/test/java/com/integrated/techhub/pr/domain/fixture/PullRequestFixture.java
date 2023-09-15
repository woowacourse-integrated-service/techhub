package com.integrated.techhub.pr.domain.fixture;

import com.integrated.techhub.pr.domain.PullRequest;
import com.integrated.techhub.pr.domain.type.Status;

public class PullRequestFixture {

    public static PullRequest 풀_리퀘스트_생성(Long memberId, Long missionId) {
        return PullRequest.builder()
                .memberId(memberId)
                .missionId(missionId)
                .title("[MVC 구현하기 - 2단계] 베베(최원용) 미션 제출합니다.")
                .status(Status.OPEN)
                .build();
    }

}
