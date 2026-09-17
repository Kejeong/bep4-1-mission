package com.back.global.eventPublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventPublisher {  // 발행을 중계하는 클래스
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(Object event) {
        applicationEventPublisher.publishEvent(event);
    }
}