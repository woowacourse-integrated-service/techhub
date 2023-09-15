package com.integrated.techhub.pr.presentation;

import com.integrated.techhub.common.acceptance.AcceptanceTest;
import com.integrated.techhub.pr.domain.PullRequest;
import com.integrated.techhub.pr.domain.repository.PullRequestRepository;
import com.integrated.techhub.pr.domain.type.Status;
import com.integrated.techhub.pr.dto.response.GetPullRequestResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static com.integrated.techhub.pr.presentation.PullRequestControllerSteps.예상_조회_결과;

@DisplayName("풀 리퀘스트 인수 테스트")
class PullRequestControllerTest extends AcceptanceTest {

    @Autowired
    private PullRequestRepository pullRequestRepository;

    @Test
    void 풀_리퀘스트를_멤버_아이디로_조회한다() {
        // given
        List<PullRequest> 저장한_풀_리퀘스트들 = 풀_리퀘스트들을_저장한다();
        List<GetPullRequestResponse> 예상_조회_결과 = 예상_조회_결과(List.of(저장한_풀_리퀘스트들.get(0)));

        // when
        var 응답 = PullRequestControllerSteps.멤버_아이디로_풀_리퀘스트_조회_요청(저장한_풀_리퀘스트들.get(0).getId());

        // then
        응답.response().then().statusCode(HttpStatus.OK.value());
    }

    private List<PullRequest> 풀_리퀘스트들을_저장한다() {
        ArrayList<PullRequest> pullRequests = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            PullRequest pullRequest = PullRequest.builder()
                    .memberId(i)
                    .missionId(i)
                    .title("[MVC 구현하기 - 2단계] 베베(최원용) 미션 제출합니다.")
                    .status(Status.OPEN)
                    .build();
            pullRequests.add(pullRequest);
        }
        pullRequestRepository.saveAll(pullRequests);
        return pullRequests;
    }

}