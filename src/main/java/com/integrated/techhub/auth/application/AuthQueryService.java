package com.integrated.techhub.auth.application;

import com.integrated.techhub.mail.dto.MailSendRequest;
import com.integrated.techhub.member.domain.repository.MemberRepository;
import com.integrated.techhub.member.exception.MemberExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthQueryService {

    private final MemberRepository memberRepository;

    public void validateExistedMember(final MailSendRequest request) {
        if (memberRepository.existByEmail(request.email())) {
            throw new MemberExistsException();
        }
    }

}
