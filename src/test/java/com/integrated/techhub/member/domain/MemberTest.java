package com.integrated.techhub.member.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MemberTest {

    @Test
    void 생성자를_생성할수_있다() {
        assertDoesNotThrow(() -> new Member("moomin@gmail.com", "moomin12", "무민",
                5, "moomin", "홍길동", "반갑습니다", Position.BE));
    }

}