package com.integrated.techhub.member.domain;

import com.integrated.techhub.mail.domain.AuthorityCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AuthorityCodeTest {

    @Test
    void 생성자를_생성할수_있다() {
        assertDoesNotThrow(() -> new AuthorityCode("email@email.com", 987654));
    }

}