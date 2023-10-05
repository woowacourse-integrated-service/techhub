package com.integrated.techhub.auth.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@RedisHash(value = "refresh_token")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    private Long id;

    @Indexed
    private String email;

    private String token;

    private String authority;

    @Indexed
    private String organization;

    @TimeToLive
    private Long ttl;

}
