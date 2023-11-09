package com.integrated.techhub.pr.application;

import com.integrated.techhub.auth.application.client.dto.response.GithubPrInfoResponse;
import com.integrated.techhub.mission.domain.Step;
import com.integrated.techhub.mission.domain.repository.StepRepository;
import com.integrated.techhub.mission.exception.StepNotFoundException;
import com.integrated.techhub.pr.domain.PullRequest;
import com.integrated.techhub.pr.domain.repository.PullRequestRepository;
import com.integrated.techhub.sse.SseEmittersInMemoryRepository;
import com.integrated.techhub.sse.exception.SseConnectionRefusedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class PullRequestService {
    private final StepRepository stepRepository;
    private final PullRequestRepository pullRequestRepository;
    private final SseEmittersInMemoryRepository sseEmittersInMemoryRepository;

    // TODO: Require Refactor
    public void create(final Long memberId, final List<GithubPrInfoResponse> prsByRepoName) {
        final List<PullRequest> prs = new ArrayList<>();
        for (GithubPrInfoResponse pr : prsByRepoName) {
            final List<Long> stepsInTitle = getStepInTitle(pr.title());
            for (Long stepNumber : stepsInTitle) {
                final Step step = stepRepository.getByNumber(stepNumber);
                final PullRequest pullRequest = pr.toPullRequest(memberId, step.getId());
                prs.add(pullRequest);
            }
        }
        isNotExistSave(prs);
    }

    private List<Long> getStepInTitle(final String title) {
        final List<Long> steps = new ArrayList<>();
        final Pattern pattern = Pattern.compile("\\d+");
        final Matcher matcher = pattern.matcher(title);
        while (matcher.find()) {
            final Long step = Long.valueOf(matcher.group());
            steps.add(step);
        }
        if (steps.isEmpty()) {
            throw new StepNotFoundException(title);
        }
        return steps;
    }

    private void isNotExistSave(final List<PullRequest> newPrs) {
        for (PullRequest newPr : newPrs) {
            if (!pullRequestRepository.existsByStepIdAndTitle(newPr.getStepId(), newPr.getTitle())) {
                pullRequestRepository.save(newPr);
            }
        }
    }

}
