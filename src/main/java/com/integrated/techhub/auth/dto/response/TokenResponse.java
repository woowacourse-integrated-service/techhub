package com.integrated.techhub.auth.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {

}
