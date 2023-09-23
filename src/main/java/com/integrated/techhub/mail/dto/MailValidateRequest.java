package com.integrated.techhub.mail.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MailValidateRequest(
        @Email(message = "이메일 형식이어야 합니다. 올바른 형식인지 확인해주세요.")
        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String email,

        @NotNull(message = "Null이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        Integer authorityCode
) {

}
