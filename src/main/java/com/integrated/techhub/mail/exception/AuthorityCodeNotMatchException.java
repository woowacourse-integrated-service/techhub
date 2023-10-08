package com.integrated.techhub.mail.exception;

import com.integrated.techhub.common.exception.ErrorCode;
import com.integrated.techhub.common.exception.TechHubException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class AuthorityCodeNotMatchException extends TechHubException {

    public AuthorityCodeNotMatchException() {
        super(new ErrorCode(BAD_REQUEST, "일치하는 인증 코드를 찾을 수 없습니다. 인증 코드와 만료 기간이 유효한지 확인해주세요."));
    }

}
