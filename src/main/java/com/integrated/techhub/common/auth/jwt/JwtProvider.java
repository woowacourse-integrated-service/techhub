package com.integrated.techhub.common.auth.jwt;

import com.integrated.techhub.auth.domain.RefreshToken;
import com.integrated.techhub.auth.domain.repository.RefreshTokenRepository;
import com.integrated.techhub.common.auth.jwt.exception.TokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public String generateAccessToken(final String email) {
        return generateToken(email, jwtProperties.accessExp());
    }

    public RefreshToken generateRefreshToken(final String email) {
        final String refreshToken = generateToken(email, jwtProperties.refreshExp());
        return refreshTokenRepository.save(RefreshToken.builder()
                .email(email)
                .token(refreshToken)
                .ttl(jwtProperties.refreshExp())
                .build());
    }

    private String generateToken(final String email, final Long tokenExp) {
        return Jwts.builder()
                .setSubject(email)
                .claim("email", email)
                .signWith(HS256, jwtProperties.secretKey())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExp * 1000))
                .setIssuedAt(new Date())
                .compact();
    }

    public String getPayload(final String token) {
        return validateParseJws(token).getBody().getSubject();
    }

    public Jws<Claims> validateParseJws(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.secretKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("토큰 만료임");
        } catch (Exception e) {
            throw new TokenInvalidException();
        }
    }

}
