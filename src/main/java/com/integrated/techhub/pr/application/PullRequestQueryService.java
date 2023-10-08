package com.integrated.techhub.pr.application;

import com.integrated.techhub.pr.dto.response.PullRequestResponse;
import com.integrated.techhub.pr.infra.dto.PullRequestQueryResponse;
import com.integrated.techhub.pr.infra.persist.PullRequestQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PullRequestQueryService {

    private final PullRequestQueryRepository pullRequestQueryRepository;

    public List<PullRequestResponse> getMyPullRequestsByMissionId(final Long memberId, final Long missionId) {
        final List<PullRequestQueryResponse> pullRequestsQueryResponses = pullRequestQueryRepository.findByMemberIdAndMissionId(memberId, missionId);
        return pullRequestsQueryResponses.stream()
                .map(PullRequestResponse::from)
                .toList();
    }

}
