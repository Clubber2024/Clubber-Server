package com.clubber.ClubberServer.domain.recruit.service;

import com.clubber.ClubberServer.domain.calendar.dto.CreateCalendarRequest;
import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarAppender;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarReader;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar.CreateLinkedCalendarRequest;
import com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar.CreateLinkedCalenderResponse;
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
    private final CalendarAppender calendarAppender;

    public CreateLinkedCalenderResponse createLinkedCalender(CreateLinkedCalendarRequest request) {
        Recruit recruit = recruitReader.findRecruitById(request.recruitId());
        CreateCalendarRequest calendarRequest = CreateCalendarRequest.from(recruit, request.recruitUrl());
        Calendar savedCalendar = calendarAppender.append(calendarRequest.toEntity());
        return new CreateLinkedCalenderResponse(request.recruitId(), savedCalendar.getId());
    }

    public void unlinkCalendar(Long calendarId) {
        Calendar calendar = calendarReader.readById(calendarId);
        Recruit recruit = recruitReader.findByCalendar(calendar);
        recruit.unlink();
    }
}
