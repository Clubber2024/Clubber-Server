package com.clubber.domain.domains.club.exception;

import com.clubber.common.exception.BaseException;

public class HashtagNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new HashtagNotFoundException();

    private HashtagNotFoundException() {
        super(ClubErrorCode.HASHTAG_NOT_FOUND);
    }
}
