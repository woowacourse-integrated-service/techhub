package com.integrated.techhub.pr.application;

import com.integrated.techhub.pr.domain.PullRequest;
import com.integrated.techhub.pr.domain.repository.PullRequestRepository;
import com.integrated.techhub.pr.dto.response.GetPullRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PullRequestQueryService {

    private final PullRequestRepository pullRequestRepository;

    public List<GetPullRequestResponse> getPullRequestByMemberId(Long memberId) {
        final List<PullRequest> pullRequests = pullRequestRepository.findByMemberId(memberId);
        return pullRequests.stream()
                .map(GetPullRequestResponse::of)
                .toList();
    }

}
