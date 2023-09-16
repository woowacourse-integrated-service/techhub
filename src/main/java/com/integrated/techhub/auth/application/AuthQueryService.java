package com.integrated.techhub.auth.application;

import com.integrated.techhub.member.domain.repository.MemberRepository;
import com.integrated.techhub.member.exception.MemberExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthQueryService {

    private final MemberRepository memberRepository;

    public void validateExistedMember(final String email) {
        if (memberRepository.existByEmail(email)) {
            throw new MemberExistsException();
        }
    }

}
