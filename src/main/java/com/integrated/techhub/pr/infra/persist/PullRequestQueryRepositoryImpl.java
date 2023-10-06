package com.integrated.techhub.pr.infra.persist;

import com.integrated.techhub.pr.domain.PullRequest;
import com.integrated.techhub.pr.infra.dto.PullRequestQueryResponse;
import com.integrated.techhub.pr.infra.dto.QPullRequestQueryResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.integrated.techhub.mission.domain.QMission.mission;
import static com.integrated.techhub.mission.domain.QStep.*;
import static com.integrated.techhub.pr.domain.QPullRequest.pullRequest;

@Repository
@RequiredArgsConstructor
public class PullRequestQueryRepositoryImpl implements PullRequestQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PullRequestQueryResponse> findByMemberIdAndMissionId(final Long memberId, final Long missionId) {
        return queryFactory.select(new QPullRequestQueryResponse(
                        pullRequest.id,
                        step.number,
                        pullRequest.title,
                        pullRequest.status.stringValue()
                ))
                .from(pullRequest)
                .innerJoin(step).on(pullRequest.stepId.eq(step.id))
                .innerJoin(step.mission, mission)
                .fetchJoin()
                .where(
                        mission.id.eq(step.mission.id),
                        pullRequest.memberId.eq(memberId),
                        mission.id.eq(missionId)
                )
                .fetch();
    }

}
