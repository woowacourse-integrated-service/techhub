package com.integrated.techhub.auth.dto.request;

public record LoginRequest(
        String email,
        String password
) {

}
