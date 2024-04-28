package com.clubber.ClubberServer.global.jwt;


import static com.clubber.ClubberServer.global.jwt.JwtProperties.ACCESS_EXP;
import static com.clubber.ClubberServer.global.jwt.JwtProperties.ACCESS_TOKEN;
import static com.clubber.ClubberServer.global.jwt.JwtProperties.MILLI_TO_SECOND;
import static com.clubber.ClubberServer.global.jwt.JwtProperties.SECRET_KEY;
import static com.clubber.ClubberServer.global.jwt.JwtProperties.TOKEN_ISSUER;
import static com.clubber.ClubberServer.global.jwt.JwtProperties.TOKEN_TYPE;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;

public class JwtTokenProvider {

    private Jws<Claims> getJws(String token){
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
    }

    public String generateAccessToken(Long id){
        final Key encodedKey = getSecretKey();
        final Date issuedAt = new Date();
        final Date accessTokenExpiresIn =
                new Date(issuedAt.getTime() + ACCESS_EXP * MILLI_TO_SECOND);

        return Jwts.builder()
                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim(TOKEN_TYPE, ACCESS_TOKEN)
                .setExpiration(accessTokenExpiresIn)
                .signWith(encodedKey)
                .compact();
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private boolean isAccessToken(String token){
        Jws<Claims> jws = getJws(token);
        return jws.getBody().get(TOKEN_TYPE).equals(ACCESS_TOKEN);
    }

    public Long parseAccessToken(String token){
        Claims claims = getJws(token).getBody();
        return Long.parseLong(claims.getSubject()); 
    }
}
