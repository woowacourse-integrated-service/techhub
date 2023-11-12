package com.integrated.techhub.pr.infra.persist;

import com.integrated.techhub.pr.domain.type.SortBy;
import com.integrated.techhub.pr.infra.dto.PullRequestQueryResponse;
import com.integrated.techhub.pr.infra.dto.QPullRequestQueryResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.integrated.techhub.mission.domain.QMission.mission;
import static com.integrated.techhub.mission.domain.QStep.step;
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
                        pullRequest.state.stringValue()
                ))
                .from(pullRequest)
                .innerJoin(step).on(pullRequest.stepId.eq(step.id))
                .innerJoin(step.mission, mission)
                .where(
                        mission.id.eq(step.mission.id),
                        pullRequest.memberId.eq(memberId),
                        mission.id.eq(missionId)
                )
                .fetch();
    }

    @Override
    public List<PullRequestQueryResponse> findSortAndOrderBy(final String sortBy, final Long missionId) {
        return queryFactory.select(new QPullRequestQueryResponse(
                        pullRequest.id,
                        step.number,
                        pullRequest.title,
                        pullRequest.state.stringValue()
                ))
                .from(pullRequest)
                .innerJoin(step).on(pullRequest.stepId.eq(step.id))
                .innerJoin(step.mission, mission)
                .where(mission.id.eq(missionId))
                .orderBy(getSorted(sortBy))
                .fetch();
    }

    private OrderSpecifier getSorted(final String sortBy) {
        if (SortBy.TITLE.name().equals(sortBy)) return pullRequest.title.desc();
        if (SortBy.STEP.name().equals(sortBy)) return pullRequest.stepId.desc();
        return pullRequest.id.desc();
    }

}
