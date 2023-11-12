package com.integrated.techhub.pr.exception;

import com.integrated.techhub.common.exception.ErrorCode;
import com.integrated.techhub.common.exception.TechHubException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class SortByNotFoundException extends TechHubException {
    public SortByNotFoundException(final String sortBy) {
        super(new ErrorCode(NOT_FOUND, String.format("%s는 알맞은 정렬 기준이 아닙니다. 알맞은 값인지 다시 확인해주세요.", sortBy)));
    }
}
