package com.integrated.techhub.pr.presentation;

import com.integrated.techhub.auth.application.GithubOAuthClientQueryService;
import com.integrated.techhub.auth.dto.response.OAuthCrewGithubPrResponse;
import com.integrated.techhub.common.auth.annotation.Auth;
import com.integrated.techhub.common.auth.resolver.AuthProperties;
import com.integrated.techhub.member.domain.Member;
import com.integrated.techhub.member.domain.repository.MemberRepository;
import com.integrated.techhub.pr.application.PullRequestQueryService;
import com.integrated.techhub.pr.application.PullRequestService;
import com.integrated.techhub.pr.dto.response.PullRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pull-requests")
@RequiredArgsConstructor
public class PullRequestController {

    private final MemberRepository memberRepository;
    private final PullRequestService pullRequestService;
    private final PullRequestQueryService pullRequestQueryService;
    private final GithubOAuthClientQueryService githubOAuthClientQueryService;

    @GetMapping("/my/{missionId}")
    public ResponseEntity<List<PullRequestResponse>> getLoginUserPullRequestsByMissionId(
            @Auth final AuthProperties authProperties,
            @PathVariable final Long missionId
    ) {
        final List<PullRequestResponse> responses = pullRequestQueryService.getMyPullRequestsByMissionId(authProperties.memberId(), missionId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/sync")
    public ResponseEntity<List<OAuthCrewGithubPrResponse>> syncMyPrsByRepoName(
            @Auth final AuthProperties authProperties,
            @RequestParam final String repoName
    ) {
        final Member member = memberRepository.getById(authProperties.memberId());
        final List<OAuthCrewGithubPrResponse> allPrsByRepoName = githubOAuthClientQueryService.getPrsByRepoName(authProperties.memberId(), repoName);
        final List<OAuthCrewGithubPrResponse> myPrs = allPrsByRepoName.stream()
                .filter(pr -> pr.title().contains(member.getNickname()))
                .toList();
        pullRequestService.create(authProperties.memberId(), myPrs);
        return ResponseEntity.ok().build();
    }

}
