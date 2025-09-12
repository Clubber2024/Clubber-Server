package com.clubber.global.event.exceptionalarm.async;

import com.clubber.global.helper.SpringEnvironmentHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
@RequiredArgsConstructor
public class AsyncExceptionAlarmPublisher {
    public final SpringEnvironmentHelper springEnvironmentHelper;
    private final ApplicationEventPublisher publisher;

    public void publishEvent(Throwable e, Method method, Object[] params) {
        publisher.publishEvent(new AsyncExceptionAlaramEvent(e, method, params));
    }
}
