package com.integrated.techhub.common.auth.jwt;

import com.integrated.techhub.common.auth.jwt.exception.TokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;

    public String create(String payload) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + jwtProperties.accessExp());
        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(jwtProperties.getEncodedSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getPayload(String token) {
        return validateParseJws(token).getBody().getSubject();
    }

    public Jws<Claims> validateParseJws(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.getEncodedSecretKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            throw new TokenInvalidException();
        }
    }

}
