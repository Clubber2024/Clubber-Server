package com.clubber.ClubberServer.domain.calendar.service;

import com.clubber.ClubberServer.domain.calendar.dto.*;
import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarAppender;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarService {
    private final CalendarAppender calendarAppender;
    private final CalendarReader calendarReader;

    public CreateCalendarResponse createCalendar(CreateCalendarRequest request) {
        Calendar calendar = request.toEntity();
        Calendar savedCalendar = calendarAppender.append(calendar);
        return CreateCalendarResponse.from(savedCalendar);
    }

    public GetCalendarResponse getCalendar(Long id) {
        Calendar calendar = calendarReader.readById(id);
        return GetCalendarResponse.from(calendar);
    }

    public void updateCalendar(UpdateCalendarRequest request, Long recruitId) {
        Calendar calendar = calendarReader.readById(recruitId);
        calendarAppender.update(calendar, request);
    }

    public void deleteCalendar(Long calendarId) {
        Calendar calendar = calendarReader.readById(calendarId);
        calendarAppender.delete(calendar);
    }
}
