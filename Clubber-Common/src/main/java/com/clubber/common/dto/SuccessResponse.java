package com.clubber.common.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SuccessResponse {

	private final boolean success = true;
	private final LocalDateTime timeStamp;
	private final Object data;

	public SuccessResponse(Object data) {
		this.data = data;
		this.timeStamp = LocalDateTime.now();
	}
}
