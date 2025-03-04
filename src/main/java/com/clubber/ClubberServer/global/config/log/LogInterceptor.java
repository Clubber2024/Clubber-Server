package com.clubber.ClubberServer.global.config.log;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler) throws Exception {
		String requestId = UUID.randomUUID().toString();
		response.setHeader("X-Request-Id", requestId);

		log.info("Request Method: [{}] URL: [{}] Params: [{}] id: [{}]", request.getMethod(),
			request.getRequestURL().toString(), getRequestParams(request), requestId);
		return true;
	}

	private Map<String, String> getRequestParams(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String paramterName = parameterNames.nextElement();
			paramMap.put(paramterName, request.getParameter(paramterName));
		}
		return paramMap;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
		Object handler, Exception ex) {
		String requestId = response.getHeader("X-Request-Id");
		log.info("Response Status: [{}], id: [{}]", response.getStatus(), requestId);
	}
}
