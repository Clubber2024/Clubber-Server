package com.clubber.ClubberServer.global.util;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomAuthStringGeneratorUtil {

	private static final SecureRandom secureRandom = new SecureRandom();

	public static String generateRandomMixCharNSpecialChar(int length) {
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
