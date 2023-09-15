package com.integrated.techhub.pr.presentation;

import com.integrated.techhub.pr.domain.PullRequest;
import com.integrated.techhub.pr.dto.response.GetPullRequestResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.List;

import static com.integrated.techhub.common.acceptance.AcceptanceSteps.given;

public class PullRequestControllerSteps {

    public static List<GetPullRequestResponse> 예상_조회_결과(List<PullRequest> 예상_풀_리퀘스트들) {
        return 예상_풀_리퀘스트들.stream()
                .map(GetPullRequestResponse::of)
                .toList();
    }

    public static ExtractableResponse<Response> 멤버_아이디로_풀_리퀘스트_조회_요청(Long memberId) {
        return given()
                .when()
                .get("/prs/" + memberId)
                .then()
                .extract();
    }

}
