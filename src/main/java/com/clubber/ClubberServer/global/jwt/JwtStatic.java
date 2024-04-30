package com.clubber.ClubberServer.global.jwt;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtStatic {
    public static final int MILLI_TO_SECOND = 1000;

    public static final String TOKEN_TYPE = "type";
    public static final String TOKEN_ISSUER = "Clubber";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String TOKEN_ROLE = "role";
}
