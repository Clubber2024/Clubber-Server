package com.clubber.ClubberServer.domain.club.exception;

import com.clubber.ClubberServer.global.error.BaseException;

public class HashtagNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new HashtagNotFoundException();

    public HashtagNotFoundException() {
        super(ClubErrorCode.HASHTAG_NOT_FOUND);
    }
}
