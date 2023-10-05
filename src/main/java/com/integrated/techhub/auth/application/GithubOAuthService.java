package com.integrated.techhub.auth.application;

import com.integrated.techhub.auth.domain.repository.AccessTokenRepository;
import com.integrated.techhub.auth.domain.repository.RefreshTokenRepository;
import com.integrated.techhub.auth.dto.response.OAuthCrewGithubUsernameResponse;
import com.integrated.techhub.auth.dto.response.OAuthTokensResponse;
import com.integrated.techhub.member.domain.Member;
import com.integrated.techhub.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GithubOAuthService {

    private final MemberRepository memberRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void setGithubTokens(
            final String email,
            final OAuthTokensResponse tokensResponse,
            final OAuthCrewGithubUsernameResponse githubUsernameResponse
    ) {
        Member member = memberRepository.getByEmail(email);
        accessTokenRepository.save(tokensResponse.toAccessToken(member.getEmail()));
        refreshTokenRepository.save(tokensResponse.toRefreshToken(member.getEmail()));
        member.updateGithubUsername(githubUsernameResponse.username());
    }

}
