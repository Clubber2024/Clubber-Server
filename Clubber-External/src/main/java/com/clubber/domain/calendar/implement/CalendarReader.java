package com.clubber.domain.calendar.implement;

import com.clubber.domain.calendar.domain.Calendar;
import com.clubber.domain.calendar.domain.CalendarStatus;
import com.clubber.domain.calendar.domain.OrderStatus;
import com.clubber.domain.calendar.dto.GetAlwaysCalendarResponse;
import com.clubber.domain.calendar.dto.GetCalendarDuplicateRequest;
import com.clubber.domain.calendar.exception.CalendarNotFoundException;
import com.clubber.domain.calendar.repository.CalendarRepository;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.recruit.domain.RecruitType;
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

    public Calendar readById(Long id) {
        return calendarRepository.findCalendarByIdAndIsDeleted(id, false)
                .orElseThrow(() -> CalendarNotFoundException.EXCEPTION);
    }

    public List<Calendar> findNonAlwaysCalendars(YearMonth recruitYearMonth) {
        List<RecruitType> recruitTypes = List.of(RecruitType.REGULAR, RecruitType.ADDITIONAL);
        LocalDateTime startOfMonth = getStartOfMonth(recruitYearMonth);
        LocalDateTime startOfNextMonth = getStartOfNextMonth(recruitYearMonth);
        return calendarRepository.findCalendarsWithinDateRange(startOfMonth, startOfNextMonth, recruitTypes);
    }

    public List<GetAlwaysCalendarResponse> findAlwaysCalendar(YearMonth recruitYearMonth) {
        LocalDateTime startOfNextMonth = getStartOfNextMonth(recruitYearMonth);
        return calendarRepository.findAlwaysRecruitCreatedBefore(startOfNextMonth, RecruitType.ALWAYS);
    }

    public Page<Calendar> readClubCalendarPage(Club club, CalendarStatus calendarStatus, RecruitType recruitType, Pageable pageable, OrderStatus orderStatus) {
        return calendarRepository.findCalendarByClubAndIsDeleted(club, calendarStatus, recruitType, pageable, orderStatus);
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

    public List<Club> getTodayEndCalendars() {
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime tomorrowStart = todayStart.plusDays(1);
        return calendarRepository.findTodayDistinctCalendar(todayStart, tomorrowStart);
    }

    public List<Calendar> findAlwaysNextCalendar(YearMonth recruitYearMonth, Long calendarId, Long clubId) {
        LocalDateTime startOfMonth = getStartOfMonth(recruitYearMonth);
        LocalDateTime startOfNextMonth = getStartOfNextMonth(recruitYearMonth);
        return calendarRepository.findNextCalendar(startOfMonth, startOfNextMonth, RecruitType.ALWAYS, calendarId, clubId);
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
