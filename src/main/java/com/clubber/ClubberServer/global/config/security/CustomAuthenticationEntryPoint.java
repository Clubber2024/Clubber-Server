package com.clubber.ClubberServer.global.config.security;

import com.clubber.ClubberServer.global.dto.ErrorResponse;
import com.clubber.ClubberServer.global.exception.BaseErrorCode;
import com.clubber.ClubberServer.global.exception.GlobalErrorCode;
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

	/**
	 * filter (JwtProvider)에서 발생하는 모든 401에러는 다음과 같은 공통 응답으로 처리한다.
	 * PermitAll()한 API는 거치지 않는다.
	 * AccessToken 만료일 경우에만 클라이언트에서 401을 응답받고 재발급 요청을 처리한다.
	 * -> 그 의외의 401에러는 별도로 커스텀 처리하지 않는다. (잘못된 토큰 등)
	 */
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
