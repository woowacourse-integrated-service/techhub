package com.integrated.techhub.auth.application;

import com.integrated.techhub.auth.domain.AccessToken;
import com.integrated.techhub.auth.domain.PasswordEncoder;
import com.integrated.techhub.auth.domain.RefreshToken;
import com.integrated.techhub.auth.domain.repository.AccessTokenRepository;
import com.integrated.techhub.auth.domain.repository.RefreshTokenRepository;
import com.integrated.techhub.auth.dto.request.SignUpRequest;
import com.integrated.techhub.auth.dto.response.TokenResponse;
import com.integrated.techhub.common.auth.jwt.JwtProvider;
import com.integrated.techhub.member.domain.Member;
import com.integrated.techhub.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final AuthQueryService authQueryService;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public Long registerMember(final SignUpRequest request) {
        authQueryService.validateExistedMember(request.email());
        final Member member = request.toEntity();
        member.encodePassword(passwordEncoder);
        return memberRepository.save(member).getId();
    }

    public TokenResponse getTokens(final String email, final String password) {
        final Member member = memberRepository.getByEmail(email);
        member.validateMatchPassword(passwordEncoder, password);
        final String accessToken = jwtProvider.generateAccessToken(member.getId());
        final String refreshToken = jwtProvider.generateRefreshToken(member.getId()).getToken();
        return new TokenResponse(accessToken, refreshToken);
    }

    public void saveGithubTokens(final AccessToken accessToken, final RefreshToken refreshToken) {
        accessTokenRepository.save(accessToken);
        refreshTokenRepository.save(refreshToken);
    }
}
