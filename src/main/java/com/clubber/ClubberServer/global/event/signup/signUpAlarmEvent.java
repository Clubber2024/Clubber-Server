package com.clubber.ClubberServer.global.event.signup;

import com.clubber.ClubberServer.domain.admin.domain.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class signUpAlarmEvent {

	private String clubName;
	private Contact contact;
}
