package com.integrated.techhub.auth.domain;

import com.integrated.techhub.auth.domain.type.Type;
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
    private Long memberId;

    @Indexed
    private Type type;

    private String token;

    @TimeToLive
    private Long ttl;

}
