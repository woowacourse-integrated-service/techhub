package com.integrated.techhub.mail.exception;

import com.integrated.techhub.common.exception.ErrorCode;
import com.integrated.techhub.common.exception.TechHubException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class AuthorityCodeNotFoundException extends TechHubException {

    public AuthorityCodeNotFoundException() {
        super(new ErrorCode(NOT_FOUND, "인증코드가 일치하지 않습니다. 인증코드를 알맞게 입력했는지 확인해주세요."));
    }

}
