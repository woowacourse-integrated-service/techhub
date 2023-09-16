package com.integrated.techhub.member.domain;

import com.integrated.techhub.mail.exception.AuthorityCodeNotMatchException;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "authority_code", timeToLive = 180)
public class AuthorityCode {

    @Id
    private String email;

    private Integer value;

    public AuthorityCode(String email, Integer value) {
        this.email = email;
        this.value = value;
    }

    public void validateAuthorityCode(int authorityCode) {
        if (!value.equals(authorityCode)) {
            throw new AuthorityCodeNotMatchException();
        }
    }

}
