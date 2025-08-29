package com.clubber.ClubberServer.global.event.signup;

import com.clubber.ClubberServer.domain.admin.domain.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpAlarmEventPublisher {

	private final ApplicationEventPublisher publisher;

	public void throwSignUpAlarmEvent(String clubName, Contact contact) {
		publisher.publishEvent(new signUpAlarmEvent(clubName, contact));
	}
}
