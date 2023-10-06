package com.integrated.techhub.auth.application;

import com.integrated.techhub.auth.domain.PasswordEncoder;
import com.integrated.techhub.auth.dto.request.SignUpRequest;
import com.integrated.techhub.auth.dto.response.TokenResponseDto;
import com.integrated.techhub.common.auth.jwt.JwtProvider;
import com.integrated.techhub.member.domain.Member;
import com.integrated.techhub.member.domain.repository.MemberRepository;
import com.integrated.techhub.member.exception.MemberAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final AuthQueryService authQueryService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public Long registerMember(final SignUpRequest request) {
        authQueryService.validateExistedMember(request.email());
        final Member member = request.toEntity();
        member.encodePassword(passwordEncoder);
        return memberRepository.save(member).getId();
    }

    public TokenResponseDto getAccessToken(final String email, final String password) {
        final Member member = memberRepository.getByEmail(email);
        member.matchedPassword(passwordEncoder, password);
        final String accessToken = jwtProvider.generateAccessToken(member.getId());
        final String refreshToken = jwtProvider.generateRefreshToken(member.getId()).getToken();
        return new TokenResponseDto(accessToken, refreshToken);
    }

}
