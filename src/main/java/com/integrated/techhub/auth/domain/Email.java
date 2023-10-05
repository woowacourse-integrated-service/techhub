package com.integrated.techhub.auth.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("email")
public class Email {

    @Id
    private Long id;

    @Indexed
    private String email;

    @TimeToLive
    private long ttl;

    public Email(final String email, final long ttl) {
        this.email = email;
        this.ttl = ttl;
    }

}