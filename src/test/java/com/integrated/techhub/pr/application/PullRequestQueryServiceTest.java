package com.integrated.techhub.pr.application;

import com.integrated.techhub.pr.domain.PullRequest;
import com.integrated.techhub.pr.domain.repository.PullRequestRepository;
import com.integrated.techhub.pr.dto.response.GetPullRequestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.integrated.techhub.pr.domain.fixture.PullRequestFixture.풀_리퀘스트_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PullRequestQueryServiceTest {

    @Autowired
    private PullRequestRepository pullRequestRepository;

    @Autowired
    private PullRequestQueryService pullRequestQueryService;

    @Test
    void 멤버_아이디로_풀_리퀘스트를_조회한다() {
        // given
        PullRequest pullRequest = 풀_리퀘스트_생성(1L, 1L);
        pullRequestRepository.save(pullRequest);

        // when
        List<GetPullRequestResponse> pullRequests = pullRequestQueryService.getPullRequestByMemberId(1L);

        // then
        Assertions.assertAll(
                () -> assertThat(pullRequests).hasSize(1),
                () -> assertThat(pullRequests.get(0).id()).isEqualTo(1)
        );
    }

}