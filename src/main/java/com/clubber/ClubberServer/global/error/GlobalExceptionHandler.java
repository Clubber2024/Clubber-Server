package com.clubber.ClubberServer.global.error;


import com.clubber.ClubberServer.global.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(
            BaseException e, HttpServletRequest request){
        BaseErrorCode code = e.getErrorCode();
        ErrorReason errorReason = code.getErrorReason();
        ErrorResponse errorResponse = new ErrorResponse(errorReason,
                request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.valueOf(errorReason.getStatus()))
                .body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
            HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;

        HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
        String url = UriComponentsBuilder.newInstance()
                .scheme(httpServletRequest.getScheme())
                .host(httpServletRequest.getServerName())
                .port(httpServletRequest.getServerPort())
                .path(httpServletRequest.getRequestURI())
                .query(httpServletRequest.getQueryString())
                .build()
                .toUriString();

        ErrorResponse errorResponse =
                new ErrorResponse(statusCode.value(), ex.getMessage(), url);
        return super.handleExceptionInternal(ex, errorResponse, headers, statusCode, request);
    }

    @SneakyThrows
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        //에러 필드 : 에러 메시지 구성
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder errorMessages = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            errorMessages.append("에러 필드: ").append(fieldError.getField());
            errorMessages.append("입력 값: ").append(fieldError.getRejectedValue());
            errorMessages.append("에러 메시지: ").append(fieldError.getDefaultMessage());
        }


        //uri 추출
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        String uri = servletWebRequest.getRequest().getRequestURI();

        ErrorResponse errorResponse = new ErrorResponse(status.value(), errorMessages.toString(), uri);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
