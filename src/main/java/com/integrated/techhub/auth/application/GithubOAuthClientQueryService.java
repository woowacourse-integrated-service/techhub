package com.integrated.techhub.auth.application;

import com.integrated.techhub.auth.domain.AccessToken;
import com.integrated.techhub.auth.domain.RefreshToken;
import com.integrated.techhub.auth.domain.repository.AccessTokenRepository;
import com.integrated.techhub.auth.domain.repository.RefreshTokenRepository;
import com.integrated.techhub.auth.dto.response.OAuthCrewGithubPrResponse;
import com.integrated.techhub.auth.exception.GithubRefreshTokenNotFoundException;
import com.integrated.techhub.member.domain.Member;
import com.integrated.techhub.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GithubOAuthClientQueryService {

    private final MemberRepository memberRepository;
    private final GithubOAuthClient githubOAuthClient;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public List<OAuthCrewGithubPrResponse> getCrewPrList(Long memberId, final String repo) {
        final Member member = memberRepository.getById(memberId);
        final Optional<AccessToken> accessTokenOptional = accessTokenRepository.findByEmail(member.getEmail());
        final Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByEmail(member.getEmail());

        if (accessTokenOptional.isPresent()) {
            final String accessToken = accessTokenOptional.get().getToken();
            return githubOAuthClient.getCrewPrInfoList(member, accessToken, repo);
        }
        if (refreshTokenOptional.isPresent()) {
            final String refreshToken = refreshTokenOptional.get().getToken();
            final String accessToken = githubOAuthClient.getNewAccessToken(refreshToken).accessToken();
            return githubOAuthClient.getCrewPrInfoList(member, accessToken, repo);
        }
        throw new GithubRefreshTokenNotFoundException();
    }

}
