package com.clubber.ClubberServer.domain.admin.util;

public class AdminUtil {
    public static String maskUsername(String username) {
        return username.replaceAll("[0-9]", "*");
    }
}
