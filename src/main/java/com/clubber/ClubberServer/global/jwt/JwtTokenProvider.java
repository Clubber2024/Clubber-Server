package com.clubber.ClubberServer.global.jwt;


import static com.clubber.ClubberServer.global.jwt.JwtStatic.ACCESS_TOKEN;
import static com.clubber.ClubberServer.global.jwt.JwtStatic.MILLI_TO_SECOND;
import static com.clubber.ClubberServer.global.jwt.JwtStatic.REFRESH_TOKEN;
import static com.clubber.ClubberServer.global.jwt.JwtStatic.TOKEN_ISSUER;
import static com.clubber.ClubberServer.global.jwt.JwtStatic.TOKEN_ROLE;
import static com.clubber.ClubberServer.global.jwt.JwtStatic.TOKEN_TYPE;


import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.global.dto.AccessTokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private Jws<Claims> getJws(String token){
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
    }

    public String generateAccessToken(User user){
        final Key encodedKey = getSecretKey();
        final Date issuedAt = new Date();
        final Date accessTokenExpiresIn =
                new Date(issuedAt.getTime() + jwtProperties.getAccessExp() * MILLI_TO_SECOND);

        return Jwts.builder()
                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(issuedAt)
                .setSubject(user.getId().toString())
                .claim(TOKEN_TYPE, ACCESS_TOKEN)
                .claim(TOKEN_ROLE, user.getRole())
                .setExpiration(accessTokenExpiresIn)
                .signWith(encodedKey)
                .compact();
    }

    public String generateRefreshToken(Long id){
        final Key encodedKey = getSecretKey();
        final Date issuedAt = new Date();
        final Date refreshTokenExpiresIn =
                new Date(issuedAt.getTime() + jwtProperties.getRefreshExp() * MILLI_TO_SECOND);

        return Jwts.builder()
                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim(TOKEN_TYPE, REFRESH_TOKEN)
                .setExpiration(refreshTokenExpiresIn)
                .signWith(encodedKey)
                .compact();
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    private boolean isAccessToken(String token){
        Jws<Claims> jws = getJws(token);
        return jws.getBody().get(TOKEN_TYPE).equals(ACCESS_TOKEN);
    }

    public AccessTokenInfo parseAccessToken(String token){
        Claims claims = getJws(token).getBody();
        return AccessTokenInfo.builder()
                .userId(Long.parseLong(claims.getSubject()))
                .role((String) claims.get(TOKEN_ROLE))
                .build();
    }

    public Long parseRefreshToken(String token){
        Claims claims = getJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    public Long getRefreshTokenTTlSecond(){
        return jwtProperties.getRefreshExp();
    }
}
