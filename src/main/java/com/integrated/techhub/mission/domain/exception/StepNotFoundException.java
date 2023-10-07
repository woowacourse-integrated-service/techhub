package com.integrated.techhub.mission.domain.exception;

import com.integrated.techhub.common.exception.ErrorCode;
import com.integrated.techhub.common.exception.TechHubException;
import org.springframework.http.HttpStatus;

public class StepNotFoundException extends TechHubException {

    public StepNotFoundException(final String title) {
        super(new ErrorCode(HttpStatus.NOT_FOUND, String.format("%s인 PR 제목에서 Step을 찾을 수 없습니다. 제목에 Step이 존재하는지 확인해주세요.", title)));
    }

    public StepNotFoundException(Long stepNumber) {
        super(new ErrorCode(HttpStatus.NOT_FOUND, String.format("미션에서 step%d를 찾을 수 없습니다. 해당 step이 존재하는지 확인해주세요.", stepNumber)));
    }

}
