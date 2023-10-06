package com.integrated.techhub.pr.presentation;

import com.integrated.techhub.auth.application.GithubOAuthClientQueryService;
import com.integrated.techhub.auth.dto.response.OAuthCrewGithubPrResponse;
import com.integrated.techhub.common.auth.annotation.Auth;
import com.integrated.techhub.common.auth.resolver.AuthProperties;
import com.integrated.techhub.pr.application.PullRequestQueryService;
import com.integrated.techhub.pr.dto.response.GetPullRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pull-requests")
@RequiredArgsConstructor
public class PullRequestController {

    private final PullRequestQueryService pullRequestQueryService;
    private final GithubOAuthClientQueryService githubOAuthClientQueryService;

//    @GetMapping("/my")
//    public ResponseEntity<List<GetPullRequestResponse>> getLoginUserPullRequestsByRepoName(
//            @Auth AuthProperties authProperties,
//            @RequestParam String repoName
//    ) {
//        pullRequestQueryService.getMyPullRequestsByRepoName(authProperties.memberId(), repoName);
//    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<GetPullRequestResponse>> searchPullRequest(@PathVariable Long memberId) {
        List<GetPullRequestResponse> pullRequests = pullRequestQueryService.getPullRequestByMemberId(memberId);
        return ResponseEntity.ok().body(pullRequests);
    }

    @GetMapping
    public ResponseEntity<List<OAuthCrewGithubPrResponse>> getPrsByRepoName(
            @Auth final AuthProperties authProperties,
            @RequestParam final String repoName
    ) {
        final List<OAuthCrewGithubPrResponse> crewPrList = githubOAuthClientQueryService.getCrewPrList(authProperties.memberId(), repoName);
        return ResponseEntity.ok().body(crewPrList);
    }

}
