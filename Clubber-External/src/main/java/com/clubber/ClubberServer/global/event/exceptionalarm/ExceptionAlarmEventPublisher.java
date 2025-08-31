package com.clubber.ClubberServer.global.event.exceptionalarm;

import com.clubber.ClubberServer.global.helper.SpringEnvironmentHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

@Service
@RequiredArgsConstructor
public class ExceptionAlarmEventPublisher {

	public final SpringEnvironmentHelper springEnvironmentHelper;
	private final ApplicationEventPublisher publisher;

	public void throwExceptionAlarmEvent(Exception e, WebRequest request) {
		if (springEnvironmentHelper.isProdProfile()) {
			publisher.publishEvent(new ExceptionAlarmEvent(e, request));
		}
	}
}
