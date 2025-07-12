package com.clubber.ClubberServer.domain.calendar.implement;

import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.calendar.exception.CalendarNotFoundException;
import com.clubber.ClubberServer.domain.calendar.repository.CalendarRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarReader {
    private final CalendarRepository calendarRepository;

    public Calendar readById(Long id) {
        return calendarRepository.findCalendarByIdAndIsDeleted(id, false)
                .orElseThrow(() -> CalendarNotFoundException.EXCEPTION);
    }

    public Page<Calendar> readClubCalendarPage(Club club, Pageable pageable) {
        return calendarRepository.findCalendarByClubAndIsDeleted(club, false, pageable);
    }

    public boolean isExistInSameMonth(RecruitType recruitType, YearMonth recruitYearMonth) {
        LocalDateTime startOfRecruitMonth = recruitYearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfRecruitMonth = recruitYearMonth.atEndOfMonth().atTime(23, 59, 59);

        YearMonth nowYearMonth = YearMonth.from(LocalDateTime.now());
        LocalDateTime startOfThisMonth = nowYearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfThisMonth = nowYearMonth.atEndOfMonth().atTime(23, 59, 59);

        return calendarRepository.isExistByRecruitTypeAndBetweenPeriod(recruitType, startOfRecruitMonth, endOfRecruitMonth, startOfThisMonth, endOfThisMonth);
    }
}
