package com.integrated.techhub.sse;

import com.integrated.techhub.common.auth.annotation.Auth;
import com.integrated.techhub.common.auth.resolver.AuthProperties;
import com.integrated.techhub.sse.exception.SseConnectionRefusedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
@RequiredArgsConstructor
public class SseController {

    // 2시간
    private static final Long DEFAULT_TIMEOUT = 180L * 1000;
    private final SseEmittersInMemoryRepository sseEmittersInMemoryRepository;

    @GetMapping(value = "/connect", produces = TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(
            @Auth final AuthProperties authProperties
    ) {
        final SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        sseEmittersInMemoryRepository.save(authProperties.memberId(), sseEmitter);
        try {
            sseEmitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected"));
        } catch (IOException e) {
            throw new SseConnectionRefusedException();
        }
        return ResponseEntity.ok(sseEmitter);
    }

}
