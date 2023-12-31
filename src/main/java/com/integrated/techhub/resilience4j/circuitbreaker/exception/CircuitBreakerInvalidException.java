package com.integrated.techhub.resilience4j.circuitbreaker.exception;

import com.integrated.techhub.common.exception.ErrorCode;
import com.integrated.techhub.common.exception.TechHubException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CircuitBreakerInvalidException extends TechHubException {

    public CircuitBreakerInvalidException(Throwable t) {
        super(new ErrorCode(NOT_FOUND, String.format("깃허브 서버가 불안정합니다. 다른 API를 사용해주세요. message: %s", t.getMessage())));
    }

}
