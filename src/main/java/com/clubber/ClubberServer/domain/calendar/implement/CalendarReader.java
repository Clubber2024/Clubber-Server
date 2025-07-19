package com.clubber.ClubberServer.domain.calendar.implement;

import com.clubber.ClubberServer.domain.calendar.dto.CalendarFilterType;
import com.clubber.ClubberServer.domain.calendar.dto.GetAlwaysCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.calendar.exception.CalendarNotFoundException;
import com.clubber.ClubberServer.domain.calendar.repository.CalendarRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarReader {

    private final CalendarRepository calendarRepository;

    public Calendar readById(Long id) {
        return calendarRepository.findCalendarByIdAndIsDeleted(id, false)
                .orElseThrow(() -> CalendarNotFoundException.EXCEPTION);
    }

    public List<Calendar> findCalendarsByDateRangeAndTypes(LocalDateTime startOfMonth,
                                                           LocalDateTime endOfMonth,
                                                           List<RecruitType> recruitTypes) {
        return calendarRepository.findCalendarsWithinDateRange(startOfMonth, endOfMonth,
                recruitTypes);
    }

    public List<GetAlwaysCalendarResponse> findCalendarsByEndDateAndType(LocalDateTime endOfMonth,
                                                                         RecruitType recruitType) {
        return calendarRepository.findAlwaysRecruitCreatedBefore(endOfMonth, recruitType);
    }

    public PageResponse<GetCalendarResponse> readClubCalendarPage(Club club, CalendarFilterType calendarFilterType, Pageable pageable) {
        Page<Calendar> calendarPages = calendarRepository.findCalendarByClubAndIsDeleted(club, calendarFilterType, pageable);
        Page<GetCalendarResponse> pageDtos = calendarPages.map(GetCalendarResponse::from);
        return PageResponse.of(pageDtos);
    }

    public boolean isExistInSameMonth(RecruitType recruitType, YearMonth recruitYearMonth, Club club) {
        LocalDateTime startOfRecruitMonth = recruitYearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfRecruitMonth = recruitYearMonth.atEndOfMonth().atTime(23, 59, 59);

        YearMonth nowYearMonth = YearMonth.from(LocalDateTime.now());
        LocalDateTime startOfThisMonth = nowYearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfThisMonth = nowYearMonth.atEndOfMonth().atTime(23, 59, 59);

        return calendarRepository.isExistByRecruitTypeAndBetweenPeriod(recruitType, club, startOfRecruitMonth, endOfRecruitMonth, startOfThisMonth, endOfThisMonth);
    }
}
