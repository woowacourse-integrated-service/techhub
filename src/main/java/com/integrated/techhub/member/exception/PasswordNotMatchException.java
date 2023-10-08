package com.integrated.techhub.member.exception;

import com.integrated.techhub.common.exception.ErrorCode;
import com.integrated.techhub.common.exception.TechHubException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class PasswordNotMatchException extends TechHubException {

    public PasswordNotMatchException() {
        super(new ErrorCode(FORBIDDEN, "유저의 비밀번호가 일치하지 않습니다."));
    }

}
