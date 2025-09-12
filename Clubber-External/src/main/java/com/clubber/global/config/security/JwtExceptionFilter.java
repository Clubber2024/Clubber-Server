package com.clubber.global.config.security;

import com.clubber.global.dto.ErrorResponse;
import com.clubber.common.exception.BaseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (BaseException e) {
			responseToClient(response,
				getErrorResponse(e, request.getRequestURI().toString()));
		}
	}

	private void responseToClient(HttpServletResponse response, ErrorResponse errorResponse)
		throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(errorResponse.getStatus());
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	}

	private ErrorResponse getErrorResponse(BaseException e, String path) {
		return new ErrorResponse(e.getErrorReason(), path);
	}
}
