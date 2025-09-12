package com.clubber.domain.notice.exception;

import com.clubber.common.exception.BaseException;

public class NoticeNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new NoticeNotFoundException();

    private NoticeNotFoundException() {
        super(NoticeErrorCode.NOTICE_NOT_FOUND);
    }
}
