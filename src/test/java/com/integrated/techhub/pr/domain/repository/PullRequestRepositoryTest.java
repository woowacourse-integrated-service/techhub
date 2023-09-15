package com.integrated.techhub.pr.domain.repository;

import com.integrated.techhub.pr.domain.PullRequest;
import com.integrated.techhub.pr.domain.fixture.PullRequestFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.integrated.techhub.pr.domain.fixture.PullRequestFixture.풀_리퀘스트_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PullRequestRepositoryTest {

    @Autowired
    private PullRequestRepository pullRequestRepository;

    @Test
    void 멤버_아이디와_일치하는_풀_리퀘스트를_모두_조회한다() {
        // given
        // TODO: Member, Mission 도메인 만들어지면 memberId, missionId 동적 할당
        PullRequest pullRequest = 풀_리퀘스트_생성(1L, 1L);
        pullRequestRepository.save(pullRequest);

        // when
        List<PullRequest> pullRequests = pullRequestRepository.findByMemberId(1L);

        // then
        Assertions.assertAll(
                () -> assertThat(pullRequests).hasSize(1),
                () -> assertThat(pullRequests.get(0).getMissionId()).isEqualTo(1)
        );
    }

}