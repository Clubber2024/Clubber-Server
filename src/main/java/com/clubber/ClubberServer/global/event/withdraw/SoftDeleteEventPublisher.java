package com.clubber.ClubberServer.global.event.withdraw;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SoftDeleteEventPublisher {

	private final ApplicationEventPublisher eventPublisher;

	public void throwSoftDeleteEvent(Long adminId) {
		eventPublisher.publishEvent(new SoftDeleteEvent(adminId));
	}
}
