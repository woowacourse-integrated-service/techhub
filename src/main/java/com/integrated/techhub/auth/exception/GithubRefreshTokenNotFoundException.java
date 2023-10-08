package com.integrated.techhub.auth.exception;

import com.integrated.techhub.common.exception.ErrorCode;
import com.integrated.techhub.common.exception.TechHubException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class GithubRefreshTokenNotFoundException extends TechHubException {

    public GithubRefreshTokenNotFoundException() {
        super(new ErrorCode(NOT_FOUND, "깃허브 리프레시 토큰을 찾을 수 없습니다. 다시 인증해주세요."));
    }

}
