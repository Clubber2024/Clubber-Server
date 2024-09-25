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

    public static final String localServer = "localhost:8080";

    public static final String localClient = "http://localhost:3000";

    public static final String remoteClient = "http://13.125.141.171";

    public static final String IMAGE_SERVER = "https://image.ssuclubber.com/"; 
}
