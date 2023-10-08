package com.integrated.techhub.auth.application.client;

import com.integrated.techhub.auth.application.client.dto.response.GithubPrInfoResponse;
import com.integrated.techhub.auth.domain.AccessToken;
import com.integrated.techhub.auth.domain.RefreshToken;
import com.integrated.techhub.auth.domain.repository.AccessTokenRepository;
import com.integrated.techhub.auth.domain.repository.RefreshTokenRepository;
import com.integrated.techhub.auth.exception.GithubRefreshTokenNotFoundException;
import com.integrated.techhub.member.domain.Member;
import com.integrated.techhub.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.integrated.techhub.auth.domain.type.Type.GITHUB;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GithubClientQueryService {

    private final MemberRepository memberRepository;
    private final RestTemplateGithubClient restTemplateGithubClient;
    private final WebClientGithubClient webClientGithubClient;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public List<GithubPrInfoResponse> getPrsByRepoName(final Long memberId, final String repo) {
        final Member member = memberRepository.getById(memberId);
        final Optional<AccessToken> accessTokenOptional = accessTokenRepository.findByMemberIdAndType(member.getId(), GITHUB);
        final Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByMemberIdAndType(member.getId(), GITHUB);

        // 액세스 토큰이 만료되지 않았다면 액세스 토큰으로 요청
        if (accessTokenOptional.isPresent()) {
            final String accessToken = accessTokenOptional.get().getToken();
            List<GithubPrInfoResponse> prsByRepoName = webClientGithubClient.getPrsByRepoName(accessToken, repo);
            System.out.println("API 요청 모두 완료");
            return prsByRepoName;
        }
        // 액세스 토큰이 만료되었다면 리프레시 토큰으로 액세스 토큰 재발급 후 재발급 받은 액세스 토큰으로 요청
        if (refreshTokenOptional.isPresent()) {
            final String refreshToken = refreshTokenOptional.get().getToken();
            final String accessToken = restTemplateGithubClient.getNewAccessToken(refreshToken).accessToken();
            return webClientGithubClient.getPrsByRepoName(accessToken, repo);
        }
        // 리프레시 토큰까지 만료되었다면 유저에게 재로그인 요청
        throw new GithubRefreshTokenNotFoundException();
    }

}
