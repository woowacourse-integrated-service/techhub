package com.integrated.techhub.pr.application;

import com.integrated.techhub.pr.dto.response.PullRequestResponse;
import com.integrated.techhub.pr.exception.PullRequestRankingResponse;
import com.integrated.techhub.pr.infra.dto.PullRequestQueryResponse;
import com.integrated.techhub.pr.infra.persist.PullRequestQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PullRequestQueryService {

    private final RedisTemplate redisTemplate;
    private final PullRequestQueryRepository pullRequestQueryRepository;

    public List<PullRequestResponse> getMyPullRequestsByMissionId(final Long memberId, final Long missionId) {
        final List<PullRequestQueryResponse> pullRequestsQueryResponses = pullRequestQueryRepository.findByMemberIdAndMissionId(memberId, missionId);
        return pullRequestsQueryResponses.stream()
                .map(PullRequestResponse::from)
                .toList();
    }

    public List<PullRequestRankingResponse> getPrsSortBy(final Long missionId) {
        final String key = missionId + " ranking";
        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringStringZSetOperations.reverseRangeWithScores(key, 0, 10);
        return typedTuples.stream()
                .map(tuple -> PullRequestRankingResponse.toResponse(tuple))
                .toList();
    }

}
