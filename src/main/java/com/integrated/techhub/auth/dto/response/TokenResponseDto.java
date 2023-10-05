package com.integrated.techhub.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public record TokenResponseDto(
        String accessToken,
        String refreshToken,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        ZonedDateTime refreshTokenExpireAt
) {

}
