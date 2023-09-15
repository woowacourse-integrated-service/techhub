package com.integrated.techhub.pr.presentation;

import com.integrated.techhub.pr.application.PullRequestQueryService;
import com.integrated.techhub.pr.dto.response.GetPullRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prs")
@RequiredArgsConstructor
public class PullRequestController {

    private final PullRequestQueryService pullRequestQueryService;

    @GetMapping("/{memberId}")
    public ResponseEntity<List<GetPullRequestResponse>> searchPullRequest(@PathVariable Long memberId) {
        List<GetPullRequestResponse> pullRequests = pullRequestQueryService.getPullRequestByMemberId(memberId);
        return ResponseEntity.ok().body(pullRequests);
    }
}
