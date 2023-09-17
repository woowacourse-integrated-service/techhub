package com.integrated.techhub.member.fixture;

import com.integrated.techhub.auth.dto.SignUpRequest;
import com.integrated.techhub.member.domain.Member;
import com.integrated.techhub.member.domain.Position;

public class MemberFixture {

    public static final SignUpRequest 무민_회원가입_요청 = new SignUpRequest(
            "moomin@gmail.com",
            "moomin12",
            "무민",
            "ParkMooMin",
            "홍길동",
            "BE",
            5
    );

    public static final Member 무민 = Member.builder()
            .email("moomin@gmail.com")
            .password("moomin12")
            .nickname("무민")
            .cardinalNumber(5)
            .githubUsername("moomin")
            .name("홍길동")
            .bio("반갑습니다.")
            .position(Position.BE)
            .build();

}
