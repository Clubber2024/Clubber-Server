package com.clubber.domain.recruit.exception;

import com.clubber.common.exception.BaseException;

import static com.clubber.domain.recruit.exception.RecruitErrorCode.RECRUIT_ALREADY_CALENDAR_UNLINKED;

public class RecruitAlreadyCalendarUnlinkedException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitAlreadyCalendarUnlinkedException();

    private RecruitAlreadyCalendarUnlinkedException() {
        super(RECRUIT_ALREADY_CALENDAR_UNLINKED);
    }
}
