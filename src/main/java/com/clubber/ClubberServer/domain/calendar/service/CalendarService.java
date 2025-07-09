package com.clubber.ClubberServer.domain.calendar.service;


import com.clubber.ClubberServer.domain.calendar.dto.GetAlwaysCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarInListResponse;
import com.clubber.ClubberServer.domain.calendar.dto.GetNonAlwaysCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarReader;
import com.clubber.ClubberServer.domain.calendar.implement.CalendarValidator;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarService {

    private final CalendarReader calendarReader;
    private final CalendarValidator calendarValidator;

    @Transactional(readOnly = true)
    public GetCalendarInListResponse getCalendarList(int year, int month) {

        calendarValidator.validateCalendarMonth(month);

        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endOfMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        List<RecruitType> recruitTypes = List.of(RecruitType.REGULAR, RecruitType.ADDITIONAL);
        List<Calendar> nonAlwaysCalendars = calendarReader.findCalendarsByDateRangeAndTypes(
            startOfMonth, endOfMonth,
            recruitTypes);
        List<GetNonAlwaysCalendarResponse> nonAlwaysCalendarDto = nonAlwaysCalendars.stream()
            .map(GetNonAlwaysCalendarResponse::from)
            .toList();

        List<GetAlwaysCalendarResponse> alwaysCalendarDto = calendarReader.findCalendarsByEndDateAndType(
            endOfMonth, RecruitType.ALWAYS);

        return GetCalendarInListResponse.of(year, month, nonAlwaysCalendarDto, alwaysCalendarDto);

    }

}
