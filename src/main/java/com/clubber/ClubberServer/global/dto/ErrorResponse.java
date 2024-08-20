package com.clubber.ClubberServer.global.dto;


import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.ErrorReason;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private final boolean success = false;
    private final int status;
    private final String code;
    private final String reason;
    private final String path;
    private final LocalDateTime timestamp;

    public ErrorResponse(ErrorReason errorReason, String path) {
        this.status = errorReason.getStatus();
        this.code = errorReason.getCode();
        this.reason = errorReason.getReason();
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int status, String reason, String path) {
        this.status = status;
        this.code = String.valueOf(status);
        this.reason = reason;
        this.timestamp= LocalDateTime.now();
        this.path = path;
    }
}
