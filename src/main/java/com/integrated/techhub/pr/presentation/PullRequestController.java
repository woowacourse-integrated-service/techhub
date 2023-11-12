package com.integrated.techhub.pr.presentation;

import com.integrated.techhub.auth.application.client.GithubClientQueryService;
import com.integrated.techhub.auth.application.client.dto.response.GithubPrInfoResponse;
import com.integrated.techhub.common.auth.annotation.Auth;
import com.integrated.techhub.common.auth.resolver.AuthProperties;
import com.integrated.techhub.member.domain.Member;
import com.integrated.techhub.member.domain.repository.MemberRepository;
import com.integrated.techhub.pr.application.PullRequestQueryService;
import com.integrated.techhub.pr.application.PullRequestService;
import com.integrated.techhub.pr.dto.response.PullRequestResponse;
import com.integrated.techhub.sse.SseEmittersInMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.integrated.techhub.pr.domain.type.SortBy.validateValue;

@RestController
@RequestMapping("/pull-requests")
@RequiredArgsConstructor
public class PullRequestController {

    private final MemberRepository memberRepository;
    private final PullRequestService pullRequestService;
    private final PullRequestQueryService pullRequestQueryService;
    private final GithubClientQueryService githubClientQueryService;
    private final SseEmittersInMemoryRepository sseEmittersInMemoryRepository;

    @GetMapping("/mine/{missionId}")
    public ResponseEntity<List<PullRequestResponse>> getLoginUserPullRequestsByMissionId(
            @Auth final AuthProperties authProperties,
            @PathVariable final Long missionId
    ) {
        final List<PullRequestResponse> responses = pullRequestQueryService.getMyPullRequestsByMissionId(authProperties.memberId(), missionId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/ranking/{missionId}")
    public ResponseEntity<List<PullRequestResponse>> getRankingOrderBy(
            @PathVariable final Long missionId,
            @Param("sortBy") final String sortBy
    ) {
        validateValue(sortBy);
        final List<PullRequestResponse> responses = pullRequestQueryService.getPrsSortBy(sortBy, missionId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/sync/mine/{missionId}")
    public ResponseEntity<List<GithubPrInfoResponse>> syncMyPrsByRepoName(
            @Auth final AuthProperties authProperties,
            @RequestParam final String repoName,
            @PathVariable final Long missionId
    ) {
        final Member member = memberRepository.getById(authProperties.memberId());
        final List<GithubPrInfoResponse> allPrsByRepoName = githubClientQueryService.getPrsByRepoName(authProperties.memberId(), repoName);
        final List<GithubPrInfoResponse> myPrs = allPrsByRepoName.stream()
                .filter(pr -> pr.title().contains(member.getNickname()))
                .toList();
        pullRequestService.create(authProperties.memberId(), myPrs);
        final List<PullRequestResponse> updatedPrs = pullRequestQueryService.getMyPullRequestsByMissionId(authProperties.memberId(), missionId);
        sseEmittersInMemoryRepository.sendAllEmitters(updatedPrs);
        return ResponseEntity.ok().build();
    }

}
