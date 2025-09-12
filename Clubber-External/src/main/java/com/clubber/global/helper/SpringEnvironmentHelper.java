package com.clubber.global.helper;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEnvironmentHelper {

	private static final String PROD = "prod";
	private final Environment environment;

	public Boolean isProdProfile() {
		String[] activeProfiles = environment.getActiveProfiles();
		List<String> currentProfile = Arrays.stream(activeProfiles).toList();
		return currentProfile.contains(PROD);
	}
}
