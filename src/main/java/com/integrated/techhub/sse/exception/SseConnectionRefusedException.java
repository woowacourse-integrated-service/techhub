package com.integrated.techhub.sse.exception;

import com.integrated.techhub.common.exception.ErrorCode;
import com.integrated.techhub.common.exception.TechHubException;
import org.springframework.http.HttpStatus;

public class SseConnectionRefusedException extends TechHubException {

    public SseConnectionRefusedException() {
        super(new ErrorCode(HttpStatus.BAD_GATEWAY, "SSE 연결 중 오류가 발생했습니다. 다시 시도해주세요."));
    }
}
