package com.integrated.techhub.sse;

import com.integrated.techhub.common.auth.annotation.Auth;
import com.integrated.techhub.common.auth.resolver.AuthProperties;
import com.integrated.techhub.pr.application.PullRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SseController {

    private final PullRequestService pullRequestService;

    @GetMapping(value = "/connect", produces = TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(
            @Auth final AuthProperties authProperties
    ) {
        final SseEmitter connectedSseEmitter = pullRequestService.connectSse(authProperties.memberId());
        return ResponseEntity.ok(connectedSseEmitter);
    }

}
