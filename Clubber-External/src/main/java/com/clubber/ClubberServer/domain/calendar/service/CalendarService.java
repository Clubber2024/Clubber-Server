package com.clubber.ClubberServer.domain.calendar.service;


import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.calendar.dto.*;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarMapper;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarReader;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarValidator;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.global.common.slice.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarService {

    private final CalendarReader calendarReader;
    private final CalendarValidator calendarValidator;
    private final CalendarMapper calendarMapper;

    @Transactional(readOnly = true)
    public GetCalendarInListResponse getCalendarList(int year, int month) {
        calendarValidator.validateCalendarMonth(month);
        YearMonth recruitYearMonth = YearMonth.of(year, month);

        List<Calendar> nonAlwaysCalendars = calendarReader.findNonAlwaysCalendars(recruitYearMonth);
        List<GetAlwaysCalendarResponse> alwaysCalendars = calendarReader.findAlwaysCalendar(recruitYearMonth);
        return calendarMapper.toCalendarInListResponse(nonAlwaysCalendars, alwaysCalendars, recruitYearMonth);
    }

    @Transactional(readOnly = true)
    public GetCalendarResponse getCalendar(Long id) {
        Calendar calendar = calendarReader.readById(id);
        return GetCalendarResponse.from(calendar);
    }

    @Transactional(readOnly = true)
    public List<GetTodayCalendarResponse> getTodayCalendarResponseList() {
        List<Club> todayEndCalendars = calendarReader.getTodayEndCalendars();
        return calendarMapper.toTodayCalendarResponseList(todayEndCalendars);
    }

    @Transactional(readOnly = true)
    public SliceResponse<GetCalendarResponse> getNextCalendar(GetNextAlwaysCalendarRequest request) {
        YearMonth recruitYearMonth = YearMonth.of(request.year(), request.month());
        List<Calendar> alwaysNextCalendars = calendarReader.findAlwaysNextCalendar(recruitYearMonth, request.nowCalendarId(), request.clubId());
        return calendarMapper.toAlwaysNextCalendarSliceResponse(alwaysNextCalendars);
    }
}
