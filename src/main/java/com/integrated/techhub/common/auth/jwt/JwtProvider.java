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

import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public String generateAccessToken(final Long memberId) {
        return generateToken(memberId, jwtProperties.accessExp());
    }

    public RefreshToken generateRefreshToken(final Long memberId) {
        final String refreshToken = generateToken(memberId, jwtProperties.refreshExp());
        return refreshTokenRepository.save(RefreshToken.builder()
                .memberId(memberId)
                .token(refreshToken)
                .ttl(jwtProperties.refreshExp())
                .build());
    }

    private String generateToken(final Long memberId, final Long tokenExp) {
        return Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .claim("memberId", memberId)
                .signWith(HS256, jwtProperties.secretKey())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExp * 1000))
                .setIssuedAt(new Date())
                .compact();
    }

    public Long getPayload(final String token) {
        return Long.valueOf(validateParseJws(token).getBody().getSubject());
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
