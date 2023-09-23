package com.integrated.techhub.member.domain;

import com.integrated.techhub.auth.infra.encode.BCryptPasswordEncoder;
import com.integrated.techhub.member.fixture.MemberFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MemberTest {

    @Test
    void 생성자를_생성할수_있다() {
        assertDoesNotThrow(() -> MemberFixture.무민);
    }

    @Test
    void encodePassword() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Member 무민 = MemberFixture.무민;
        String rawPassword = 무민.getPassword();

        무민.encodePassword(bCryptPasswordEncoder);

        Assertions.assertThat(rawPassword).isNotEqualTo(무민.getPassword());
    }

}