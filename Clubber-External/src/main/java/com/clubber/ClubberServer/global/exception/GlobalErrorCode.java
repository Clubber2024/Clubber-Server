package com.clubber.ClubberServer.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum GlobalErrorCode implements BaseErrorCode {

	UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "GLOBAL_401_1", "인증되지 않은 사용자입니다"),

	INVALID_METHOD_ARGUMENT_TYPE(HttpStatus.BAD_REQUEST.value(), "GLOBAL_400_1",
		"Request Parameter가 유요한 지 확인해주세요"),

	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "GLOBAL_500_1",
		"서버 오류. 관리자에게 문의 바랍니다."),

	MAIL_NOT_SENT(HttpStatus.INTERNAL_SERVER_ERROR.value(), "GLOBAL_500_2", "메일 발송 실패");

	private final Integer status;
	private final String code;
	private final String reason;

	@Override
	public ErrorReason getErrorReason() {
		return ErrorReason.builder()
			.status(status)
			.code(code)
			.reason(reason)
			.build();
	}
}
