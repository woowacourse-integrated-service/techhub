package com.integrated.techhub.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public record TokenResponseDto(
        String accessToken,
        String refreshToken
) {

}
