package com.integrated.techhub.pr.application;

import com.integrated.techhub.auth.application.client.dto.response.OAuthCrewGithubPrResponse;
import com.integrated.techhub.member.domain.Member;
import com.integrated.techhub.member.domain.repository.MemberRepository;
import com.integrated.techhub.mission.domain.Step;
import com.integrated.techhub.mission.domain.repository.StepRepository;
import com.integrated.techhub.pr.domain.PullRequest;
import com.integrated.techhub.pr.domain.repository.PullRequestRepository;
import com.integrated.techhub.pr.exception.StepNotFoundException;
import lombok.RequiredArgsConstructor;
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

    private final StepRepository stepRepository;
    private final MemberRepository memberRepository;
    private final PullRequestRepository pullRequestRepository;

    public void create(final Long memberId, final List<OAuthCrewGithubPrResponse> prsByRepoName) {
        final List<PullRequest> prs = new ArrayList<>();
        for (OAuthCrewGithubPrResponse pr : prsByRepoName) {
            final List<Long> stepsInTitle = getStepInTitle(pr.title());
            for (Long stepNumber : stepsInTitle) {
                final Step step = stepRepository.getByNumber(stepNumber);
                final PullRequest pullRequest = pr.toPullRequest(memberId, step.getId());
                prs.add(pullRequest);
            }
        }
        isNotExistSaveOrElseUpdate(memberId, prs);
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

    private void isNotExistSaveOrElseUpdate(final Long memberId, final List<PullRequest> requests) {
        Member member = memberRepository.getById(memberId);
        for (PullRequest pullRequest : requests) {
            final List<PullRequest> prs = pullRequestRepository.findByTitleLikeNickname(member.getNickname());
            if (prs.isEmpty()) {
                pullRequestRepository.save(pullRequest);
            } else {
                for (PullRequest pr : prs) {
                    pr.update(pullRequest.getTitle(), pullRequest.getState());
                }
            }
        }
    }

}
