package com.integrated.techhub.auth.application;

import com.integrated.techhub.auth.dto.SignUpRequest;
import com.integrated.techhub.member.domain.repository.MemberRepository;
import com.integrated.techhub.member.fixture.MemberFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @Test
    void registerMember() {
        //given
        SignUpRequest request = MemberFixture.무민_회원가입_요청;

        //when
        authService.registerMember(request);

        //then
        Assertions.assertThat(memberRepository.findAll().size()).isEqualTo(1);
    }

}