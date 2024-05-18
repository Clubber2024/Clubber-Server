package com.clubber.ClubberServer.global.config.security;

import com.clubber.ClubberServer.global.dto.ErrorResponse;
import com.clubber.ClubberServer.global.error.BaseException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch (BaseException e){
            getErrorResponse(e, request.getRequestURI().toString()); 
        }
    }

    private ErrorResponse getErrorResponse(BaseException e, String path){
        return new ErrorResponse(e.getErrorReason(), path);
    }
}
