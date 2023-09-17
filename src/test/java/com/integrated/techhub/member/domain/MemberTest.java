package com.integrated.techhub.member.domain;

import com.integrated.techhub.member.fixture.MemberFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MemberTest {

    @Test
    void 생성자를_생성할수_있다() {
        assertDoesNotThrow(() -> MemberFixture.무민);
    }

}