package com.clubber.ClubberServer.domain.recruit.service;

import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarReader;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitLinkedCalendarService {
    private final RecruitReader recruitReader;
    private final CalendarReader calendarReader;

    public void unlinkCalendar(Long calendarId) {
        Calendar calendar = calendarReader.readById(calendarId);
        Recruit recruit = recruitReader.findByCalendar(calendar);
        recruit.unlink();
    }
}
