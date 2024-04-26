package com.clubber.ClubberServer.global.jwt;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtProperties {
    @Value("{$auth.jwt}")
    public static String SECRET_KEY;

    public static final Long ACCESS_EXP = 43200L; //12시간
    public static final Long REFRESH_EXP = 1209600L; //2주
    public static final int MILLI_TO_SECOND = 1000;

    public static final String TOKEN_TYPE = "type";
    public static final String TOKEN_ISSUER = "Clubber";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
}
