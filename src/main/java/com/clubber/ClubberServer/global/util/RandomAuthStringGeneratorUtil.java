package com.clubber.ClubberServer.global.util;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;


@Component
public class RandomAuthStringGeneratorUtil {

	public String generateRandomMixCharNSpecialChar(int length) {
		SecureRandom secureRandom = new SecureRandom();

		String charNSpecialChar = IntStream.concat(
				IntStream.rangeClosed(33, 47),
				IntStream.rangeClosed(58, 126))
			.mapToObj(i -> String.valueOf((char) i))
			.collect(Collectors.joining());

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append(
				charNSpecialChar.charAt(secureRandom.nextInt(charNSpecialChar.length())));
		}
		return builder.toString();
	}
}
