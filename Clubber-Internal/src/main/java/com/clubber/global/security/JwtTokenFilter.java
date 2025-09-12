package com.clubber.global.security;

import com.clubber.global.jwt.JwtTokenUtil;
import com.clubber.global.jwt.vo.AccessTokenInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

import static com.clubber.global.consts.ClubberStatic.AUTH_HEADER;
import static com.clubber.global.consts.ClubberStatic.BEARER;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);
        if (token != null) {
            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {

        Cookie accessTokenCookie = WebUtils.getCookie(request, "accessToken");
        if (accessTokenCookie != null) {
            return accessTokenCookie.getValue();
        }

        String requestHeader = request.getHeader(AUTH_HEADER);

        if (requestHeader != null
                && requestHeader.length() > BEARER.length()
                && requestHeader.startsWith(BEARER)) {
            return requestHeader.substring(BEARER.length());
        }
        return null;
    }

    private Authentication getAuthentication(String token) {
        AccessTokenInfo accessTokenInfo = jwtTokenUtil.parseAccessToken(token);

        Long id = accessTokenInfo.userId();
        String role = accessTokenInfo.role();

        log.info("[Authentication] id : [{}] role [{}]", id, role);
        UserDetails userDetails = new AuthDetails(id.toString(), role);

        return new UsernamePasswordAuthenticationToken(
                userDetails, "user", userDetails.getAuthorities()
        );
    }
}
