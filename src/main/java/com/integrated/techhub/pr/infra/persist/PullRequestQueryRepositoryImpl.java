package com.integrated.techhub.pr.infra.persist;

import com.integrated.techhub.pr.domain.PullRequest;
import com.integrated.techhub.pr.domain.repository.PullRequestQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PullRequestQueryRepositoryImpl implements PullRequestQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PullRequest> findPullRequestByMemberId(Long memberId) {
        return null;
    }


}
