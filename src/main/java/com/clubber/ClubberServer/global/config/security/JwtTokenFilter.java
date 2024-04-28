package com.clubber.ClubberServer.global.config.security;

import static com.clubber.ClubberServer.global.jwt.JwtProperties.AUTH_HEADER;
import static com.clubber.ClubberServer.global.jwt.JwtProperties.BEARER;

import com.clubber.ClubberServer.global.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

    }

    private String resolveToken(HttpServletRequest request){
        String requestHeader = request.getHeader(AUTH_HEADER);

        if (requestHeader != null
                && requestHeader.length() > BEARER.length()
                && requestHeader.startsWith(BEARER)) {
            return requestHeader.substring(BEARER.length());
        }
        return null;
    }
}
