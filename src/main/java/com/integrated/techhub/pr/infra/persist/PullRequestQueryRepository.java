package com.integrated.techhub.pr.infra.persist;

import com.integrated.techhub.pr.infra.dto.PullRequestQueryResponse;

import java.util.List;

public interface PullRequestQueryRepository {

    List<PullRequestQueryResponse> findByMemberIdAndMissionId(Long memberId, Long missionId);

    List<PullRequestQueryResponse> findSortAndOrderBy(String sortBy, Long missionId);
}
