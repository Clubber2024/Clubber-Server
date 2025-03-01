package com.clubber.ClubberServer.global.event.signup;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpAlarmEventPublisher {

	private final ApplicationEventPublisher publisher;

	public void throwReviewApproveEvent(String clubName, String contact) {
		publisher.publishEvent(new signUpAlarmEvent(clubName, contact));
	}
}
