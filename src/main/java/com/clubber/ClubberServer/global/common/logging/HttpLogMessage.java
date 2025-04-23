package com.clubber.ClubberServer.global.common.logging;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Builder
public record HttpLogMessage(
        String httpMethod,
        String requestUri,
        HttpStatus httpStatus,
        long elapsedTime,
        Map<String, String> headers,
        Map<String, String> requestParam,
        String requestBody,
        String responseBody
) {
    public static HttpLogMessage createHttpLogMessage(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper, long elapsedTime) {
        return HttpLogMessage.builder()
                .httpMethod(requestWrapper.getMethod())
                .requestUri(requestWrapper.getRequestURI())
                .httpStatus(HttpStatus.valueOf(responseWrapper.getStatus()))
                .requestBody(new String(requestWrapper.getContentAsByteArray()))
                .responseBody(new String(responseWrapper.getContentAsByteArray()))
                .headers(getRequestHeader(requestWrapper.getHeaderNames(), requestWrapper))
                .requestParam(getRequestParam(requestWrapper.getParameterNames(), requestWrapper))
                .elapsedTime(elapsedTime)
                .build();
    }

    public static Map<String, String> getRequestHeader(Enumeration<String> enumeration, ContentCachingRequestWrapper requestWrapper) {
        Map<String, String> headerMap = new HashMap<>();

        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            headerMap.put(key, requestWrapper.getHeader(key));
        }
        return headerMap;
    }

    public static Map<String, String> getRequestParam(Enumeration<String> enumeration, ContentCachingRequestWrapper requestWrapper) {
        Map<String, String> paramMap = new HashMap<>();

        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            paramMap.put(key, requestWrapper.getParameter(key));
        }
        return paramMap;
    }
}
