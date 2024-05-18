package com.clubber.ClubberServer.global.error;


import com.clubber.ClubberServer.global.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
}
