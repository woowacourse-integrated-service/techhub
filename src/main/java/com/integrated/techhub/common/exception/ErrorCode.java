package com.integrated.techhub.common.exception;

import org.springframework.http.HttpStatus;

public record ErrorCode(
        HttpStatus status,
        String message
) {

}
