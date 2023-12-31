package com.integrated.techhub.pr.application;

import com.integrated.techhub.auth.application.client.dto.response.GithubPrInfoResponse;
import com.integrated.techhub.mission.domain.Step;
import com.integrated.techhub.mission.domain.repository.StepRepository;
import com.integrated.techhub.mission.exception.StepNotFoundException;
import com.integrated.techhub.pr.domain.PullRequest;
import com.integrated.techhub.pr.domain.repository.PullRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class PullRequestService {
    private final RedisTemplate redisTemplate;
    private final StepRepository stepRepository;
    private final PullRequestRepository pullRequestRepository;

    // TODO: Require Refactor
    public void create(final Long missionId, final Long memberId, final List<GithubPrInfoResponse> prsByRepoName) {
        final List<PullRequest> prs = new ArrayList<>();
        for (GithubPrInfoResponse pr : prsByRepoName) {
            final List<Long> stepsInTitle = getStepInTitle(pr.title());
            for (Long stepNumber : stepsInTitle) {
                final Step step = stepRepository.getByNumber(stepNumber);
                final PullRequest pullRequest = pr.toPullRequest(memberId, step.getId());
                prs.add(pullRequest);
            }
        }
        isNotExistSave(missionId, prs);
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

    private void isNotExistSave(final Long missionId, final List<PullRequest> newPrs) {
        for (PullRequest newPr : newPrs) {
            if (!pullRequestRepository.existsByStepIdAndTitle(newPr.getStepId(), newPr.getTitle())) {
                redisTemplate.opsForZSet().add(missionId + " ranking", newPr.getTitle(), newPr.getStepId());
                pullRequestRepository.save(newPr);
            }
        }
    }

}
