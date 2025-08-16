package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

import static com.clubber.ClubberServer.domain.recruit.exception.RecruitErrorCode.RECRUIT_ALREADY_CALENDAR_UNLINKED;

public class RecruitAlreadyCalendarUnlinkedException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitAlreadyCalendarUnlinkedException();

    private RecruitAlreadyCalendarUnlinkedException() {
        super(RECRUIT_ALREADY_CALENDAR_UNLINKED);
    }
}
