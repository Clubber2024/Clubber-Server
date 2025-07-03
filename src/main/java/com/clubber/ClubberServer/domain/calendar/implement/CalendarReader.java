package com.clubber.ClubberServer.domain.calendar.implement;

import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.calendar.exception.CalendarNotFoundException;
import com.clubber.ClubberServer.domain.calendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarReader {
    private final CalendarRepository calendarRepository;

    public Calendar readById(Long id) {
        return calendarRepository.findById(id)
                .orElseThrow(() -> CalendarNotFoundException.EXCEPTION);
    }
}
