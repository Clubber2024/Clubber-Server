package com.clubber.ClubberServer.global.exception;

public class EnumTypeNotValidException extends BaseException {

	public static final BaseException EXCEPTION = new EnumTypeNotValidException();
	private EnumTypeNotValidException() {
		super(GlobalErrorCode.INTERNAL_SERVER_ERROR);
	}
}
