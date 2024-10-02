package com.clubber.ClubberServer.global.jwt;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtStatic {
    public static final int MILLI_TO_SECOND = 1000;

    public static final String TOKEN_TYPE = "type";
    public static final String TOKEN_ISSUER = "Clubber";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    public static final String REFRESH_TOKEN= "REFRESH_TOKEN";
    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String TOKEN_ROLE = "role";

    public static final String LOCAL_SERVER = "localhost:8080";

    public static final String LOCAL_CLIENT = "http://localhost:3000";

    public static final String PROD_CLIENT = "https://ssuclubber.com";

    public static final String DEV_CLIENT = "http://dev.ssuclubber.com";

    public static final String IMAGE_SERVER = "https://image.ssuclubber.com/"; 
}
