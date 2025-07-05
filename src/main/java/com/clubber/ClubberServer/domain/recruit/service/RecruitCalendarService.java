package com.clubber.ClubberServer.domain.recruit.service;

import com.clubber.ClubberServer.domain.admin.implement.AdminReader;
import com.clubber.ClubberServer.domain.calendar.service.CalendarService;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar.GetCalendarInListResponse;
import com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar.GetCalendarResponse;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitCalendarInvalidMonthException;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitCalendarInvalidYearException;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitReader;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitCalendarService {

    private final RecruitRepository recruitRepository;


    @Transactional(readOnly = true)
    public GetCalendarInListResponse getRecruitCalendar(int year, int month) {

        if (year != Year.now().getValue()) {
            throw RecruitCalendarInvalidYearException.EXCEPTION;
        }

        if (month < 1 || month > 12) {
            throw RecruitCalendarInvalidMonthException.EXCEPTION;
        }

        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endOfMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        List<Recruit> recruits = recruitRepository.findRecruitsWithinDateRange(startOfMonth,
            endOfMonth);

        List<GetCalendarResponse> calendarRecruits = recruits.stream()
            .map(recruit -> GetCalendarResponse.of(recruit))
            .collect(Collectors.toList());

        return GetCalendarInListResponse.of(year, month, calendarRecruits);

    }


}
