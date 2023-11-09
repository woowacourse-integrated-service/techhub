package com.integrated.techhub.sse;

import com.integrated.techhub.pr.dto.response.PullRequestResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseEmittersInMemoryRepository {

    private final Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    public SseEmitter save(final Long key, final SseEmitter sseEmitter) {
        sseEmitters.put(key, sseEmitter);
        sseEmitter.onCompletion(() -> sseEmitters.remove(key));
        sseEmitter.onTimeout(() -> sseEmitters.remove(key));
        sseEmitter.onError((e) -> sseEmitters.remove(key));
        return sseEmitter;
    }

    public void sendAllEmitters(final List<PullRequestResponse> updatedPrs) {
        sseEmitters.keySet().forEach(key -> {
            try {
                sseEmitters.get(key).send(SseEmitter.event()
                        .name("updatedPrs")
                        .data(updatedPrs));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
