package com.clubber.ClubberServer.domain.calendar.implement;

import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarDuplicateRequest;
import com.clubber.ClubberServer.domain.calendar.repository.CalendarFilterType;
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

    public boolean isExistInSameMonth(GetCalendarDuplicateRequest request, Club club) {
        YearMonth nowYearMonth = YearMonth.from(LocalDateTime.now());
        LocalDateTime startOfThisMonth = getStartOfMonth(nowYearMonth);
        LocalDateTime endOfThisMonth = getEndOfMonth(nowYearMonth);

        if (request.recruitType() == RecruitType.ALWAYS) {
            return calendarRepository.isExistByRecruitTypeAndBetweenPeriod(RecruitType.ALWAYS, club, null, null, startOfThisMonth, endOfThisMonth);
        }

        YearMonth recruitYearMonth = YearMonth.from(request.startAt());
        LocalDateTime startOfRecruitMonth = getStartOfMonth(recruitYearMonth);
        LocalDateTime endOfRecruitMonth = getEndOfMonth(recruitYearMonth);
        return calendarRepository.isExistByRecruitTypeAndBetweenPeriod(request.recruitType(), club, startOfRecruitMonth, endOfRecruitMonth, startOfThisMonth, endOfThisMonth);
    }

    private static LocalDateTime getEndOfMonth(YearMonth nowYearMonth) {
        return nowYearMonth.atEndOfMonth().atTime(23, 59, 59);
    }

    private static LocalDateTime getStartOfMonth(YearMonth nowYearMonth) {
        return nowYearMonth.atDay(1).atStartOfDay();
    }
}
