package com.clubber.ClubberServer.domain.calendar.implement;

import com.clubber.ClubberServer.domain.calendar.dto.GetAlwaysCalendarResponse;
import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.calendar.exception.CalendarNotFoundException;
import com.clubber.ClubberServer.domain.calendar.repository.CalendarRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<Calendar> readClubCalendarPage(Club club, Pageable pageable) {
        return calendarRepository.findCalendarByClubAndIsDeleted(club, false, pageable);
    }
}
