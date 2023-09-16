package com.integrated.techhub.member.exception;

import com.integrated.techhub.common.exception.ErrorCode;
import com.integrated.techhub.common.exception.TechHubException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class MemberExistsException extends TechHubException {

    public MemberExistsException() {
        super(new ErrorCode(BAD_REQUEST, "이미 존재하는 회원입니다."));
    }

}
