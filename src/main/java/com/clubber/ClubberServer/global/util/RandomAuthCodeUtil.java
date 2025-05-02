package com.clubber.ClubberServer.global.util;

import java.security.SecureRandom;

public class RandomAuthCodeUtil {
    public static SecureRandom secureRandom = new SecureRandom();

    private static final int EMAIL_AUTH_NUMBER_LENGTH = 6;

    private static Integer generateRandomIntegerByLength(int length) {
        int upperLimit = (int) Math.pow(10, length);
        return secureRandom.nextInt(upperLimit);
    }

    public static Integer getEmailAuthRandomNumber() {
        return generateRandomIntegerByLength(EMAIL_AUTH_NUMBER_LENGTH);
    }
}
