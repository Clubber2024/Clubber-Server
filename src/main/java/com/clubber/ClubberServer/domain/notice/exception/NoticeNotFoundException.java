package com.clubber.ClubberServer.domain.notice.exception;

import com.clubber.ClubberServer.domain.club.exception.ClubErrorCode;
import com.clubber.ClubberServer.domain.club.exception.ClubIdNotFoundException;
import com.clubber.ClubberServer.global.error.BaseException;

public class NoticeNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new NoticeNotFoundException();

    private NoticeNotFoundException() {
        super(NoticeErrorCode.NOTICE_NOT_FOUND);
    }
}
