package com.clubber.ClubberServer.global.exception;

import com.clubber.ClubberServer.global.error.BaseException;
import com.clubber.ClubberServer.global.error.GlobalErrorCode;

public class EnumTypeNotValidException extends BaseException {

	public static final BaseException EXCEPTION = new EnumTypeNotValidException();
	private EnumTypeNotValidException() {
		super(GlobalErrorCode.INTERNAL_SERVER_ERROR);
	}
}
