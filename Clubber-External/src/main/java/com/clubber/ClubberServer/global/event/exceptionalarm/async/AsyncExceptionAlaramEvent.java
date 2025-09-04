package com.clubber.ClubberServer.global.event.exceptionalarm.async;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

@Getter
@AllArgsConstructor
public class AsyncExceptionAlaramEvent {
    private Throwable exception;
    private Method method;
    private Object[] params;
}
