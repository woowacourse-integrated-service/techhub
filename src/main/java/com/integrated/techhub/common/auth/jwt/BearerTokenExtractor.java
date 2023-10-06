package com.integrated.techhub.common.auth.jwt;

import com.integrated.techhub.common.auth.jwt.exception.TokenInvalidException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@NoArgsConstructor(access = PRIVATE)
public class BearerTokenExtractor {

    private static final String BEARER_TYPE = "bearer ";
    private static final String BEARER_JWT_REGEX = "^bearer [A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$";

    public static String extract(final HttpServletRequest request) {
        final String BearerToken = request.getHeader(AUTHORIZATION);
        validateBearerToken(BearerToken);
        return BearerToken.replace(BEARER_TYPE, "").trim();
    }

    private static void validateBearerToken(final String bearerToken) {
        if (bearerToken == null || !bearerToken.matches(BEARER_JWT_REGEX)) {
            throw new TokenInvalidException();
        }
    }

}
