package com.clubber.global.config.async;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class ClonedTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> callerThreadContext = MDC.getCopyOfContextMap();
        return () -> {
            MDC.setContextMap(callerThreadContext);
            runnable.run();
        };
    }
}
