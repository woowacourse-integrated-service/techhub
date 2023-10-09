package com.integrated.techhub.member.application;

import com.integrated.techhub.auth.domain.PasswordEncoder;
import com.integrated.techhub.member.domain.Member;
import com.integrated.techhub.member.domain.repository.MemberRepository;
import com.integrated.techhub.member.dto.MemberChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public void changePassword(final MemberChangePasswordRequest request) {
        final Member member = memberRepository.getByEmail(request.email());

        member.changeEncodedPassword(passwordEncoder, request.newPassword());
    }

}
