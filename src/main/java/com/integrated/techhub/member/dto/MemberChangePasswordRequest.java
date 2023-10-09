package com.integrated.techhub.member.dto;

import com.integrated.techhub.common.exception.ErrorCode;
import com.integrated.techhub.common.exception.TechHubException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public record MemberChangePasswordRequest(
        String email,
        String newPassword,
        String checkPassword
) {


    public void validateSamePassword() {
        if (!newPassword.equals(checkPassword)) {
            throw new PasswordRequestNotMatchException();
        }
    }

    public class PasswordRequestNotMatchException extends TechHubException {

        public PasswordRequestNotMatchException() {
            super(new ErrorCode(BAD_REQUEST, "비밀번호가 일치하지 않습니다. 같은 비밀번호인지 확인해주세요."));
        }

    }

}
