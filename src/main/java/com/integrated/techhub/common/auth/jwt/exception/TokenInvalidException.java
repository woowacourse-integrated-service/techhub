package com.integrated.techhub.common.auth.jwt.exception;

import com.integrated.techhub.common.exception.ErrorCode;
import com.integrated.techhub.common.exception.TechHubException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class TokenInvalidException extends TechHubException {

    public TokenInvalidException() {
        super(new ErrorCode(UNAUTHORIZED, "유효하지 않은 토큰입니다. 유효한 토큰인지 확인해주세요."));
    }

}
