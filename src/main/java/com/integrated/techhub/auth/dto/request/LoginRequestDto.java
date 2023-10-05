package com.integrated.techhub.auth.dto.request;

public record LoginRequestDto(
        String email,
        String password
) {

}
