package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.domain.user.domain.AccountRole;

import java.time.LocalDateTime;

public class CalendarFixture {
    public static final String CALENDAR_TITLE = "title";
    public static final String CALENDAR_URL = "url";
    public static final LocalDateTime START_AT = LocalDateTime.of(2025, 1, 1, 0, 0);
    public static final LocalDateTime END_AT = LocalDateTime.of(2025, 1, 4, 23, 59);

    public static Calendar.CalendarBuilder aCalendar() {
        return Calendar.builder()
                .title(CALENDAR_TITLE)
                .url(CALENDAR_URL)
                .writerRole(AccountRole.ADMIN)
                .recruitType(RecruitType.ALWAYS)
                .startAt(START_AT)
                .endAt(END_AT);
    }
}
