package com.clubber.ClubberServer.domain.calendar.implement;

import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.user.domain.AccountRole;
import org.springframework.stereotype.Component;

@Component
public class CalendarMapper {

    public Calendar toCalendar(Recruit recruit, Club club, String recruitUrl) {
        return Calendar.builder()
                .title(recruit.getTitle())
                .recruitType(recruit.getRecruitType())
                .startAt(recruit.getStartAt())
                .endAt(recruit.getEndAt())
                .url(recruitUrl)
                .club(club)
                .writerRole(AccountRole.ADMIN)
                .build();
    }
}
