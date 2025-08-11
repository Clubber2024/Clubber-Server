package com.clubber.ClubberServer.domain.calendar.service;


import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.calendar.dto.GetAlwaysCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarInListResponse;
import com.clubber.ClubberServer.domain.calendar.dto.GetNonAlwaysCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarReader;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarValidator;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
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

    @Transactional(readOnly = true)
    public GetCalendarInListResponse getCalendarList(int year, int month) {

        calendarValidator.validateCalendarMonth(month);

        YearMonth recruitYearMonth = YearMonth.of(year, month);
        List<RecruitType> recruitTypes = List.of(RecruitType.REGULAR, RecruitType.ADDITIONAL);
        List<Calendar> nonAlwaysCalendars = calendarReader.findCalendarsByDateRangeAndTypes(recruitYearMonth, recruitTypes);
        List<GetNonAlwaysCalendarResponse> nonAlwaysCalendarDto = nonAlwaysCalendars.stream()
                .map(GetNonAlwaysCalendarResponse::from)
                .toList();

        List<GetAlwaysCalendarResponse> alwaysCalendarDto = calendarReader.findCalendarsByEndDateAndType(recruitYearMonth, RecruitType.ALWAYS);
        return GetCalendarInListResponse.of(year, month, nonAlwaysCalendarDto, alwaysCalendarDto);
    }
}
