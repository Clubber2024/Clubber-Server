package com.clubber.ClubberServer.global.error;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.discord.DiscordClient;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.discord.DiscordMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.clubber.ClubberServer.global.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	public final DiscordClient discorClient;

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorResponse> handleBaseException(
		BaseException e, HttpServletRequest request) {
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

		ServletWebRequest servletWebRequest = (ServletWebRequest)request;
		String uri = servletWebRequest.getRequest().getRequestURI();
		ErrorResponse errorResponse =
			new ErrorResponse(statusCode.value(), ex.getMessage(), uri);
		return super.handleExceptionInternal(ex, errorResponse, headers, statusCode, request);
	}

	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e){
		return ResponseEntity.status(GlobalErrorCode.INVALID_METHOD_ARGUMENT_TYPE.getStatus())
			.body(GlobalErrorCode.INVALID_METHOD_ARGUMENT_TYPE.getErrorReason());
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
		ServletWebRequest servletWebRequest = (ServletWebRequest)request;
		String uri = servletWebRequest.getRequest().getRequestURI();

		ErrorResponse errorResponse = new ErrorResponse(status.value(), errorMessages.toString(), uri);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
    
	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleAllException(Exception e, WebRequest request) {
		GlobalErrorCode internalServerError = GlobalErrorCode.INTERNAL_SERVER_ERROR;
		log.error("INTERNAL SERVER ERROR", e);
		sendDiscordAlarm(e, request);
		return ResponseEntity.status(internalServerError.getStatus())
			.body(internalServerError.getErrorReason());
	}

	private void sendDiscordAlarm(Exception e, WebRequest request) {
		discorClient.sendAlarm(createDiscordMessage(e, request));
	}

	private DiscordMessage createDiscordMessage(Exception e, WebRequest request) {
		List<DiscordMessage.Embed> embedList = List.of(DiscordMessage.Embed
				.builder()
				.title("에러 테스트중입니다.")
				.description("에러 설명")
				.build());

		return DiscordMessage.builder()
				.content("에러 발생 내용")
				.embeds(embedList)
				.build();
	}

	private String getErrorStackTrace(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString(); 
	}
}
