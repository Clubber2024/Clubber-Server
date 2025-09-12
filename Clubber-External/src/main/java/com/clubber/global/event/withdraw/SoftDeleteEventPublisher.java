package com.clubber.global.event.withdraw;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SoftDeleteEventPublisher {

	private final ApplicationEventPublisher eventPublisher;

	public void throwSoftDeleteEvent(Long clubId) {
		eventPublisher.publishEvent(new SoftDeleteEvent(clubId));
	}
}
