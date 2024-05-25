package com.clubber.ClubberServer.global.config.security;

import com.clubber.ClubberServer.global.dto.ErrorResponse;
import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.GlobalErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(404);
        ErrorResponse errorResponse = getErrorResponse(GlobalErrorCode.PAGE_NOT_FOUND,
                request.getRequestURI());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private ErrorResponse getErrorResponse(BaseErrorCode baseErrorCode, String path){
        return new ErrorResponse(baseErrorCode.getErrorReason(), path);
    }
}
