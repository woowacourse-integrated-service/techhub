package com.integrated.techhub.pr.domain.repository;

import com.integrated.techhub.pr.domain.PullRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PullRequestRepository extends JpaRepository<PullRequest, Long> {

    boolean existsByStepIdAndTitle(final Long stepId, final String title);

}
