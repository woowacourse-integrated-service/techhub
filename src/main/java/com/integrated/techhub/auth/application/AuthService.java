package com.integrated.techhub.auth.application;

import com.integrated.techhub.auth.dto.SignUpRequest;
import com.integrated.techhub.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final AuthQueryService authQueryService;

    public Long registerMember(final SignUpRequest request) {
        authQueryService.validateExistedMember(request.email());
        return memberRepository.save(request.toEntity()).getId();
    }

}
