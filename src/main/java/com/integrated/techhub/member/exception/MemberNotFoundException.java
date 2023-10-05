package com.integrated.techhub.member.exception;

import com.integrated.techhub.common.exception.ErrorCode;
import com.integrated.techhub.common.exception.TechHubException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class MemberNotFoundException extends TechHubException {

    public MemberNotFoundException(final Long id) {
        super(new ErrorCode(NOT_FOUND, String.format("id가 %d인 유저를 찾을 수 없습니다.", id)));
    }

    public MemberNotFoundException(final String email) {
        super(new ErrorCode(NOT_FOUND, String.format("email이 %s인 유저를 찾을 수 없습니다.", email)));
    }

}
