package com.clubber.ClubberServer.global.helper;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringEnvironmentHelper {
	private final Environment environment;

	private static final String PROD = "prod";

	public Boolean isProdProfile() {
		String[] activeProfiles = environment.getActiveProfiles();
		List<String> currentProfile = Arrays.stream(activeProfiles).toList();
		return currentProfile.contains(PROD);
	}
}
