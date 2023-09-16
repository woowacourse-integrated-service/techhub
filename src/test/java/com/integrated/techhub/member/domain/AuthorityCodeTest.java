package com.integrated.techhub.member.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorityCodeTest {

    @Test
    void 생성자를_생성할수_있다() {
        assertDoesNotThrow(() -> new AuthorityCode("email@email.com", 987654));
    }

}