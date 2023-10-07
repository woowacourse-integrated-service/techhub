package com.integrated.techhub.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@AllArgsConstructor
@RedisHash(value = "access_token")
public class AccessToken {

    @Id
    private Long id;

    @Indexed
    private Long memberId;

    private String token;

    private String type;

    @TimeToLive
    private Long ttl;

}
