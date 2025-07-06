package com.clubber.ClubberServer.domain.calendar.service;

import com.clubber.ClubberServer.domain.calendar.dto.*;
import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarAppender;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarReader;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitReader;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarService {
    private final CalendarAppender calendarAppender;
    private final CalendarReader calendarReader;
    //TODO 패키지 순환참조 해결
    private final RecruitReader recruitReader;

    public CreateCalendarResponse createCalendar(CreateCalendarRequest request) {
        Calendar calendar = request.toEntity();
        Calendar savedCalendar = calendarAppender.append(calendar);
        return CreateCalendarResponse.from(savedCalendar);
    }

    public CreateLinkedCalenderResponse createLinkedCalender(CreateLinkedCalendarRequest request) {
        Recruit recruit = recruitReader.findRecruitById(request.recruitId());
        CreateCalendarRequest calendarRequest = CreateCalendarRequest.from(recruit, request.recruitUrl());
        Calendar savedCalendar = calendarAppender.append(calendarRequest.toEntity());
        recruit.linkCalendar(savedCalendar);
        return new CreateLinkedCalenderResponse(request.recruitId(), savedCalendar.getId());
    }

    public GetCalendarResponse getCalendar(Long id) {
        Calendar calendar = calendarReader.readById(id);
        return GetCalendarResponse.from(calendar);
    }

    public void updateCalendar(UpdateCalendarRequest request, Long recruitId) {
        Calendar calendar = calendarReader.readById(recruitId);
        calendarAppender.update(calendar, request);
    }

    public void syncCalendarWithRecruit(Long recruitId, String title, RecruitType recruitType, LocalDateTime startAt, LocalDateTime endAt) {
            Calendar calendar = calendarReader.readById(recruitId);
            calendarAppender.update(calendar,title,recruitType,startAt,endAt);
    }

    public void deleteCalendar(Long calendarId) {
        Calendar calendar = calendarReader.readById(calendarId);
        calendarAppender.delete(calendar);
    }
}
