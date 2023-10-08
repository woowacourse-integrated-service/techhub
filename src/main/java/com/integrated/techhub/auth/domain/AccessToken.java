package com.integrated.techhub.auth.domain;

import com.integrated.techhub.auth.domain.type.Type;
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

    @Indexed
    private Type type;

    private String token;

    @TimeToLive
    private Long ttl;

}
