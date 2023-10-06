package com.integrated.techhub.pr.domain.repository;

import com.integrated.techhub.pr.domain.PullRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PullRequestRepository extends JpaRepository<PullRequest, Long> {

    @Query("select p from PullRequest p where p.title like %:nickname%")
    List<PullRequest> findByTitleLikeNickname(@Param("nickname") final String nickname);

}
