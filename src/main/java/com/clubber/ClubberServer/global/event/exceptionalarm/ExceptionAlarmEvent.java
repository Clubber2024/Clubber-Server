package com.clubber.ClubberServer.global.event.exceptionalarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.context.request.WebRequest;

@Getter
@AllArgsConstructor
public class ExceptionAlarmEvent {
    private Exception e;
    private WebRequest request;
}
