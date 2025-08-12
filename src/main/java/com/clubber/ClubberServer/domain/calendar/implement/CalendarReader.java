package com.clubber.ClubberServer.domain.calendar.implement;

import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.calendar.domain.CalendarStatus;
import com.clubber.ClubberServer.domain.calendar.domain.OrderStatus;
import com.clubber.ClubberServer.domain.calendar.dto.GetAlwaysCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarDuplicateRequest;
import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarResponseWithLinkedStatus;
import com.clubber.ClubberServer.domain.calendar.exception.CalendarNotFoundException;
import com.clubber.ClubberServer.domain.calendar.repository.CalendarRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitReader;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarReader {

    private final CalendarRepository calendarRepository;
    private final RecruitReader recruitReader;

    public Calendar readById(Long id) {
        return calendarRepository.findCalendarByIdAndIsDeleted(id, false)
                .orElseThrow(() -> CalendarNotFoundException.EXCEPTION);
    }

    public List<Calendar> findCalendarsByDateRangeAndTypes(
            YearMonth recruitYearMonth,
            List<RecruitType> recruitTypes) {
        LocalDateTime startOfMonth = getStartOfMonth(recruitYearMonth);
        LocalDateTime startOfNextMonth = getStartOfNextMonth(recruitYearMonth);
        return calendarRepository.findCalendarsWithinDateRange(startOfMonth, startOfNextMonth,
                recruitTypes);
    }

    public List<GetAlwaysCalendarResponse> findCalendarsByEndDateAndType(YearMonth recruitYearMonth, RecruitType recruitType) {
        LocalDateTime startOfNextMonth = getStartOfNextMonth(recruitYearMonth);
        return calendarRepository.findAlwaysRecruitCreatedBefore(startOfNextMonth, recruitType);
    }

//    public PageResponse<GetCalendarResponseWithLinkedStatus> readClubCalendarPage(Club club, CalendarFilterType calendarFilterType, Pageable pageable) {
//        Page<Calendar> calendarPages = calendarRepository.findCalendarByClubAndIsDeleted(club, calendarFilterType, pageable);
//        Page<GetCalendarResponseWithLinkedStatus> pageDtos = calendarPages.map(
//                calendar -> {
//                    boolean isCalendarLinked = recruitReader.isCalendarLinked(calendar);
//                    return GetCalendarResponseWithLinkedStatus.from(calendar, isCalendarLinked);
//                }
//        );
//        return PageResponse.of(pageDtos);
//    }

    public PageResponse<GetCalendarResponseWithLinkedStatus> readClubCalendarPageWithFilter(Club club, CalendarStatus calendarStatus, RecruitType recruitType, Pageable pageable, OrderStatus orderStatus) {
        Page<Calendar> calendarPages = calendarRepository.findCalendarByClubAndIsDeleted(club, calendarStatus, recruitType, pageable, orderStatus);
        Page<GetCalendarResponseWithLinkedStatus> pageDtos = calendarPages.map(
                calendar -> {
                    boolean isCalendarLinked = recruitReader.isCalendarLinked(calendar);
                    return GetCalendarResponseWithLinkedStatus.from(calendar, isCalendarLinked);
                }
        );
        return PageResponse.of(pageDtos);
    }

    public boolean isExistInSameMonth(GetCalendarDuplicateRequest request, Club club) {
        YearMonth nowYearMonth = YearMonth.from(LocalDateTime.now());
        LocalDateTime startOfThisMonth = getStartOfMonth(nowYearMonth);
        LocalDateTime endOfThisMonth = getStartOfNextMonth(nowYearMonth);

        if (request.recruitType() == RecruitType.ALWAYS) {
            return calendarRepository.isExistByRecruitTypeAndBetweenPeriod(RecruitType.ALWAYS, club, null, null, startOfThisMonth, endOfThisMonth);
        }

        YearMonth recruitYearMonth = YearMonth.from(request.startAt());
        LocalDateTime startOfRecruitMonth = getStartOfMonth(recruitYearMonth);
        LocalDateTime endOfRecruitMonth = getStartOfNextMonth(recruitYearMonth);
        return calendarRepository.isExistByRecruitTypeAndBetweenPeriod(request.recruitType(), club, startOfRecruitMonth, endOfRecruitMonth, startOfThisMonth, endOfThisMonth);
    }

    public List<Calendar> getTodayEndCalendars() {
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime tomorrowStart = todayStart.plusDays(1);
        return calendarRepository.findByEndAtGreaterThanEqualAndEndAtLessThanAndIsDeletedFalse(todayStart, tomorrowStart);
    }

    private static LocalDateTime getStartOfNextMonth(YearMonth yearMonth) {
        return yearMonth.plusMonths(1)
                .atDay(1)
                .atStartOfDay();
    }

    private static LocalDateTime getStartOfMonth(YearMonth nowYearMonth) {
        return nowYearMonth.atDay(1)
                .atStartOfDay();
    }
}
