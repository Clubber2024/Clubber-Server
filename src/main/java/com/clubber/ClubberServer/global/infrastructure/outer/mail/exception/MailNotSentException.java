package com.clubber.ClubberServer.global.infrastructure.outer.mail.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

import static com.clubber.ClubberServer.global.exception.GlobalErrorCode.MAIL_NOT_SENT;

public class MailNotSentException extends BaseException {
    public static final BaseException EXCEPTION = new MailNotSentException();

    private MailNotSentException() {
        super(MAIL_NOT_SENT);
    }
}
