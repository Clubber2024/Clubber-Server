package com.clubber.ClubberServer.global.infrastructure.outer.mail.exception;

import com.clubber.common.exception.BaseException;

import static com.clubber.common.exception.GlobalErrorCode.MAIL_NOT_SENT;

public class MailNotSentException extends BaseException {
    public static final BaseException EXCEPTION = new MailNotSentException();

    private MailNotSentException() {
        super(MAIL_NOT_SENT);
    }
}
