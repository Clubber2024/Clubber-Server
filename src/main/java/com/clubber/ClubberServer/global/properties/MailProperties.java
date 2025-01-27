package com.clubber.ClubberServer.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("mail")
public class MailProperties {

	private final String host;
	private final String username;
	private final String password;
}
