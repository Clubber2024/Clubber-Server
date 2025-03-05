package com.clubber.ClubberServer.global.util;

import java.security.SecureRandom;

public class RandomAuthCodeUtil {
    public static SecureRandom secureRandom = new SecureRandom();

    public static Integer generateRandomInteger(int length) {
        int upperLimit = (int) Math.pow(10, length);
        return secureRandom.nextInt(upperLimit);
    }
}
