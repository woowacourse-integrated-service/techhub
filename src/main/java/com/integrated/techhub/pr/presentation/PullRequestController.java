package com.integrated.techhub.pr.presentation;

import com.integrated.techhub.auth.application.GithubOAuthClientQueryService;
import com.integrated.techhub.auth.dto.response.OAuthCrewGithubPrResponse;
import com.integrated.techhub.common.auth.annotation.Auth;
import com.integrated.techhub.common.auth.resolver.AuthProperties;
import com.integrated.techhub.pr.application.PullRequestQueryService;
import com.integrated.techhub.pr.dto.response.PullRequestResponse;
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

    @GetMapping("/my/{missionId}")
    public ResponseEntity<List<PullRequestResponse>> getLoginUserPullRequestsByMissionId(
            @Auth AuthProperties authProperties,
            @PathVariable Long missionId
    ) {
        final List<PullRequestResponse> responses = pullRequestQueryService.getMyPullRequestsByMissionId(authProperties.memberId(), missionId);
        return ResponseEntity.ok(responses);
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
