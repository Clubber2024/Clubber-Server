package com.clubber.ClubberServer.global.config.security;

import com.clubber.domain.domains.user.exception.UserErrorCode;
import com.clubber.ClubberServer.global.dto.ErrorResponse;
import com.clubber.common.exception.BaseErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@RequiredArgsConstructor
@Component
public class AccessDeniedFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (AccessDeniedException e) {
			responseToClient(response,
				getErrorResponse(UserErrorCode.ACCESS_TOKEN_NOT_EXIST,
					request.getRequestURI().toString()));
		}
	}

	private void responseToClient(HttpServletResponse response, ErrorResponse errorResponse)
		throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(errorResponse.getStatus());
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	}

	private ErrorResponse getErrorResponse(BaseErrorCode code, String path) {
		return new ErrorResponse(code.getErrorReason(), path);
	}
}
