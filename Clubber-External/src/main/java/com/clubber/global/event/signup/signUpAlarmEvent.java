package com.clubber.global.event.signup;

import com.clubber.domain.domains.admin.domain.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class signUpAlarmEvent {

	private String clubName;
	private Contact contact;
}
