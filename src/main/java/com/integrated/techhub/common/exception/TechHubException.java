package com.integrated.techhub.common.exception;

import lombok.Getter;

@Getter
public class TechHubException extends RuntimeException {

    private final ErrorCode errorCode;

    public TechHubException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

}
