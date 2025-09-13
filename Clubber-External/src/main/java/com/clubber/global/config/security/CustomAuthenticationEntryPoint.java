package com.clubber.global.config.security;

import com.clubber.global.dto.ErrorResponse;
import com.clubber.common.exception.BaseErrorCode;
import com.clubber.common.exception.GlobalErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(GlobalErrorCode.UNAUTHORIZED.getStatus());
        ErrorResponse errorResponse = getErrorResponse(GlobalErrorCode.UNAUTHORIZED,
                request.getRequestURI());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private ErrorResponse getErrorResponse(BaseErrorCode baseErrorCode, String path) {
        return new ErrorResponse(baseErrorCode.getErrorReason(), path);
    }
}
