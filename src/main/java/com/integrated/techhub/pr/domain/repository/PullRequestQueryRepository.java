package com.integrated.techhub.pr.domain.repository;

import com.integrated.techhub.pr.domain.PullRequest;

import java.util.List;

public interface PullRequestQueryRepository {
    List<PullRequest> findPullRequestByMemberId(Long memberId);
}
