package com.integrated.techhub.auth.application;

import com.integrated.techhub.member.domain.Member;
import com.integrated.techhub.member.domain.repository.MemberRepository;
import com.integrated.techhub.member.exception.MemberAlreadyExistsException;
import com.integrated.techhub.member.fixture.MemberFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class AuthQueryServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    AuthQueryService authQueryService;

    @Test
    void validateExistedMember() {
        //given
        Member member = memberRepository.save(MemberFixture.무민);

        //when, then
        assertThrows(MemberAlreadyExistsException.class, () -> authQueryService.validateExistedMember(member.getEmail()));
    }

}