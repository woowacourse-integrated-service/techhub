package com.integrated.techhub.auth.dto;

import com.integrated.techhub.member.domain.Member;
import com.integrated.techhub.member.domain.Position;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(
        @Email(message = "이메일 형식이어야 합니다. 올바른 형식인지 확인해주세요.")
        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String email,

        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$", message = "최소 8자, 문자 1개와 숫자 1개 이상을 포함해야합니다. 다시 입력해주세요.")
        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String password,

        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String nickname,

        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String githubUserName,

        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String name,

        @Pattern(regexp = "^(BE|FE|AOS)$", message = "BE, FE, AOS만 입력할 수 있습니다. 알맞은 포지션인지 확인해주세요.")
        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String position,

        @NotNull(message = "Null이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        Integer cardinalNumber
) {

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .githubUsername(githubUserName)
                .name(name)
                .position(Position.valueOf(position))
                .cardinalNumber(cardinalNumber)
                .build();
    }

}
