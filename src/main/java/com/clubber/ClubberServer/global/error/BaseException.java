package com.clubber.ClubberServer.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private BaseErrorCode errorCode;

    public ErrorReason getErrorReason(){
        return this.errorCode.getErrorReason();
    }
}
